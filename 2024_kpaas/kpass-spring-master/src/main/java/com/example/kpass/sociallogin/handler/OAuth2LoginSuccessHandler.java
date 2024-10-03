package com.example.kpass.sociallogin.handler;


import com.example.kpass.sociallogin.provider.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = jwtProvider.createToken(authentication.getName());

        response.addHeader("Authorization", "Bearer " + token);
        System.out.println("Bearer " + token);
        //로그인 성공후 리다이렉트 될 페이지
        response.sendRedirect("http://localhost:3000/");
    }
}
