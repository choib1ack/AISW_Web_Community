package com.aisw.community.advice.handler;

import com.aisw.community.advice.ApiErrorResponse;
import com.aisw.community.advice.exception.AccessTokenExpiredException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

//    @Autowired
//    @Qualifier("handlerExceptionResolver")
//    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AccessTokenExpiredException ex) {
//            resolver.resolveException(request, response, null, ex);
            // custom error response class used across my project
            ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, "JwtTokenExpired", ex);

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
