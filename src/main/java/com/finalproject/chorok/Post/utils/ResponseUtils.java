package com.finalproject.chorok.Post.utils;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ResponseUtils {

    public HashMap<String,String> responseHashMap(HttpStatus httpCode){
        HashMap<String,String> hs = new HashMap<>();
        hs.put("StatusCode",String.valueOf(httpCode));
        hs.put("msg","성공적으로 등록되었습니다");
        return hs;
    }

}
