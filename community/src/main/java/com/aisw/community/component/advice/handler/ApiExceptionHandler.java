package com.aisw.community.component.advice.handler;

import com.aisw.community.component.advice.ApiErrorResponse;
import com.aisw.community.component.advice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PostNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("PostNotFound", "post is not found with ID: " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserNotFoundException ex) {
        ApiErrorResponse response = null;
        if(ex.getId() != null) {
             response = new ApiErrorResponse("UserNotFound", "user is not found with ID: " + ex.getId());
        } else if(ex.getUsername() != null) {
            response = new ApiErrorResponse("UserNotFound", "user is not found with username: " + ex.getUsername());
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(CommentNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("CommentNotFound", "comment is not found with ID: " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContentLikeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(ContentLikeNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ContentLikeNotFound",
                    "content like is not found with ID: " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContentLikeAlreadyExistException.class)
    public ResponseEntity<ApiErrorResponse> handleException(ContentLikeAlreadyExistException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ContentLikeAlreadyExist",
                "content like is already exist: " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(AdminNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("AdminUserNotFound", "admin user is not found with ID: " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BannerNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(BannerNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("BannerNotfound", "banner is not found with ID: " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SiteInformationNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(SiteInformationNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("CiteInformationNotfound", "cite information is not found with ID: " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SiteCategoryNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(SiteCategoryNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("SiteCategoryNotFound", "category is not found with ID: " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(FileNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("FileNotFound", "file is not found with ID: " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlertNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(AlertNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("AlertNotFound", "alert is not found with ID: " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FaqNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(FaqNotFoundException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("FaqNotFound", "faq is not found with ID: " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEqualUserException.class)
    public ResponseEntity<ApiErrorResponse> handleException(NotEqualUserException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("NotEqualAccount", "the user is not writer: " + ex.getId());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SignUpNotSuitableException.class)
    public ResponseEntity<ApiErrorResponse> handleException(SignUpNotSuitableException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("SignUpNotSuitable", "request is not suitable: " + ex.getUsername());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostStatusNotSuitableException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PostStatusNotSuitableException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("PostStatusNotSuitable", "post status is not suitable: " + ex.getStatus());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneNumberNotSuitableException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PhoneNumberNotSuitableException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("PhoneNumberNotSuitable", "phone number is not suitable: " + ex.getPhoneNumber());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentIdNotSuitableException.class)
    public ResponseEntity<ApiErrorResponse> handleException(StudentIdNotSuitableException ex) {
        ApiErrorResponse response =
                new ApiErrorResponse("StudentIdNotSuitable", "student id is not suitable: " + ex.getStudentId());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}