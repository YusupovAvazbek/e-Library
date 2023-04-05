package com.example.elibrary.security;

import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.dto.UsersDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.elibrary.service.validator.AppStatusCode.VALIDATION_ERROR_CODE;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if(authorization!=null && authorization.startsWith("Bearer ")){
            if(jwtService.isExpire(authorization.substring(7))){
                response.setContentType("application/json");
                response.getWriter().println(
                        ResponseDto.builder()
                                .code(VALIDATION_ERROR_CODE)
                                .success(false)
                                .build()
                );
            }
            UsersDto subject = jwtService.subject(authorization.substring(7));
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(subject,null,subject.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);
    }
}
