//package com.example.kpass.selflogin.filter;
//
//import com.example.kpass.selflogin.provider.JwtProvider;
//import io.jsonwebtoken.ExpiredJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtProvider jwtProvider;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = parseToken(request);
//        try {
//            if (token != null) {
//                String email = jwtProvider.validateToken(token);
//                if (email != null) {
//                    SecurityContextHolder.getContext().setAuthentication(
//                            new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>())
//                    );
//                }
//            }
//            filterChain.doFilter(request, response);
//        } catch (ExpiredJwtException e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Token has expired");
//        } catch (IllegalStateException e) {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.getWriter().write("Invalid token");
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("An unexpected error occurred");
//        }
//    }
//
//    private String parseToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        return (bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
//    }
//}
