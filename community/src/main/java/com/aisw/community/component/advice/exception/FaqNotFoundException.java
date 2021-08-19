package com.aisw.community.component.advice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FaqNotFoundException extends RuntimeException {

    private Long id;
}
