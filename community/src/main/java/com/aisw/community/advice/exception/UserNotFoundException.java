package com.aisw.community.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {

    private Long id;

    private String username;

    public UserNotFoundException(Long id) {
        this.id = id;
    }

    public UserNotFoundException(String username) {
        this.username = username;
    }
}
