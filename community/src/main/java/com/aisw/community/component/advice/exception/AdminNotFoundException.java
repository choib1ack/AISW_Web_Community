package com.aisw.community.component.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminNotFoundException extends RuntimeException {

    private Long id;
}
