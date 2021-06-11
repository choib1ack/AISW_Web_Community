package com.aisw.community.advice.handler;

import com.aisw.community.advice.ApiErrorResponse;
import com.aisw.community.advice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PostNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("PostNotFound", "post is not found with ID : " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("UserNotFound", "user is not found with ID : " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(CommentNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("CommentNotFound", "comment is not found with ID : " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(AdminNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("AdminUserNotFound", "admin user is not found with ID : " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BannerNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(BannerNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("BannerNotfound", "banner is not found with ID : " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
