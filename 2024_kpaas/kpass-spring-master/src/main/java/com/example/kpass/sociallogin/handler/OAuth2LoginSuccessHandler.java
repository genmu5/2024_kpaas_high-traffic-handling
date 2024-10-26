package com.example.kpass.sociallogin.handler;

import com.example.kpass.sociallogin.provider.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    @Value("${FRONTEND_URL//localhost:3000}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = jwtProvider.createToken(authentication.getName());



        System.out.println("su");
        //로그인 성공후 리다이렉트 될 페이지
        // 로그인 성공 후, 동적으로 설정된 FRONTEND_URL로 리다이렉트 (JWT 토큰 포함)
        response.sendRedirect(frontendUrl + "/home?token=" + token);
    }
}
