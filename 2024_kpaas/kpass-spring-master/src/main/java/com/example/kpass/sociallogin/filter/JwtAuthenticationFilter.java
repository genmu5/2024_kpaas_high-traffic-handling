//package com.example.kpass.sociallogin.filter;
//
//
//import com.example.kpass.sociallogin.entity.UserEntity;
//import com.example.kpass.sociallogin.provider.JwtProvider;
//import com.example.kpass.sociallogin.repository.UserRepository;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    private final JwtProvider jwtProvider;
//    private final UserRepository userRepository;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            String token = parseToken(request);
//            if (token != null) {
//                String userId = jwtProvider.validate(token);
//
//                if(userId == null) {
//                    filterChain.doFilter(request, response);
//                    return;
//                }
//                //fintByUserId로 User의 ID를 가져옴
//                UserEntity userEntity = userRepository.findByUserId(userId);
//                String role = userEntity.getUserRole();
//                List<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority(role));
//
//                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                securityContext.setAuthentication(authenticationToken);
//                SecurityContextHolder.setContext(securityContext);
//            }else {
//                filterChain.doFilter(request, response);
//                return;
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private String parseToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }else {
//            return null;
//        }
//    }
//}
