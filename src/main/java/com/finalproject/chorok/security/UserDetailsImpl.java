package com.finalproject.chorok.security;

import com.finalproject.chorok.Login.model.User;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
//@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
       this.user = user;
    }

    public User getUser() {
        return user;
    }
//
//    public static UserDetailsImpl initUserDetails(HashMap<String, String> userInfo) {
//        return UserDetailsImpl.builder()
//                .username(userInfo.get(JwtTokenUtils.CLAIM_USER_NAME))
//                //                .password(userInfo.get(JwtTokenUtils.CLAIM_USER_PASSWORD))
//                .build();
//    }
//
//    private String username;
//    private String password;

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("UserDetailsImpl authorities 에서 나온 결과");


        return Collections.emptyList();
    }
}