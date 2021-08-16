package com.aisw.community.component.advice.handler;

import com.aisw.community.component.advice.ApiErrorResponse;
import com.aisw.community.component.advice.exception.TokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenException ex) {
            ApiErrorResponse errorResponse = null;
            if (ex.getMessage().equals("access token")) {
                errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "JwtTokenExpired", ex);
            } else if (ex.getMessage().equals("refresh token")) {
                errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "RefreshJwtTokenExpired", ex);
            } else if (ex.getMessage().equals("invalid token")) {
                errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "InvalidToken", ex);
            }
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(convertObjectToJson(errorResponse));
        }

    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
