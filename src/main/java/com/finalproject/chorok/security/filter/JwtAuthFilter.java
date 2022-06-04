package com.finalproject.chorok.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.chorok.login.exception.CustomException;
import com.finalproject.chorok.login.exception.RestApiException;
import com.finalproject.chorok.security.jwt.HeaderTokenExtractor;
import com.finalproject.chorok.security.jwt.JwtDecoder;
import com.finalproject.chorok.security.jwt.JwtPreProcessingToken;
import com.nimbusds.oauth2.sdk.ErrorObject;
import org.hibernate.procedure.NamedParametersNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.finalproject.chorok.login.exception.ErrorCode.DONT_USE_THIS_TOKEN;
import static com.finalproject.chorok.security.jwt.JwtTokenUtils.CLAIM_EXPIRED_DATE;

/**
 * Token 을 내려주는 Filter 가 아닌  client 에서 받아지는 Token 을 서버 사이드에서 검증하는 클레스 SecurityContextHolder 보관소에 해당
 * Token 값의 인증 상태를 보관 하고 필요할때 마다 인증 확인 후 권한 상태 확인 하는 기능
 */
public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final HeaderTokenExtractor extractor;
    private final JwtDecoder jwtDecoder;

    public JwtAuthFilter(
            RequestMatcher requiresAuthenticationRequestMatcher,
            HeaderTokenExtractor extractor,
            JwtDecoder jwtDecoder
    ) {
        super(requiresAuthenticationRequestMatcher);

        this.extractor = extractor;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException, IOException {

        // JWT 값을 담아주는 변수 TokenPayload
        String tokenPayload = request.getHeader("Authorization");
        if (tokenPayload == null) {
//            response.sendRedirect("/auth/logIn");
            response.sendError(494, "로그인이 필요한 서비스입니다.");
            return null;
        }

        JwtPreProcessingToken jwtToken = new JwtPreProcessingToken(
                extractor.extract(tokenPayload, request));

        String nowToken = extractor.extract(tokenPayload, request);


        //////////////////////////////
        try {
            DecodedJWT decodedJWT = jwtDecoder.isValidToken(nowToken)
                    .orElseThrow(() -> new NamedParametersNotSupportedException("유효한 토큰 X"));
//                    .orElseThrow(() -> new CustomException(DONT_USE_THIS_TOKEN));

            Date expiredDate = decodedJWT
                    .getClaim(CLAIM_EXPIRED_DATE)
                    .asDate();

            Date now = new Date();
            if (expiredDate.before(now)) {
//            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getCode());
                throw new IllegalArgumentException("유효한 토큰이 아닙니다.");

            }
        }
        catch (NamedParametersNotSupportedException e){
            response.setStatus(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            ErrorObject errorObject = new ErrorObject("999", e.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), errorObject);
        }
        catch (IllegalArgumentException e){
            response.setStatus(HttpStatus.GATEWAY_TIMEOUT.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            ErrorObject errorObject = new ErrorObject("998", e.getMessage());
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), errorObject);
        }
//            DecodedJWT decodedJWT = jwtDecoder.isValidToken(nowToken)
//                    .orElseThrow(() -> new CustomException(DONT_USE_THIS_TOKEN));



//        if(expiredDate.before(new Date())){
//            putErrorMessage(response, "만료된 토큰입니다.");
//            return null;
//
//        }

        /////////////////////////////////////////

        return super
                .getAuthenticationManager()
                .authenticate(jwtToken);
    }


    private void putErrorMessage(HttpServletResponse response, String errorMessage) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8"); // HelloData 객체
        RestApiException exception = new RestApiException();
        exception.setHttpStatus(HttpStatus.BAD_REQUEST);
//        exception.set(HttpStatus.BAD_REQUEST);
        exception.setErrorMessage(errorMessage);
        String result = mapper.writeValueAsString(exception);
        response.getWriter().print(result);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        /*
         *  SecurityContext 사용자 Token 저장소를 생성합니다.
         *  SecurityContext 에 사용자의 인증된 Token 값을 저장합니다.
         */
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        // FilterChain chain 해당 필터가 실행 후 다른 필터도 실행할 수 있도록 연결실켜주는 메서드
        chain.doFilter(
                request,
                response
        );
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        /*
         *	로그인을 한 상태에서 Token값을 주고받는 상황에서 잘못된 Token값이라면
         *	인증이 성공하지 못한 단계 이기 때문에 잘못된 Token값을 제거합니다.
         *	모든 인증받은 Context 값이 삭제 됩니다.
         */
        SecurityContextHolder.clearContext();

        super.unsuccessfulAuthentication(
                request,
                response,
                failed
        );
    }
}
