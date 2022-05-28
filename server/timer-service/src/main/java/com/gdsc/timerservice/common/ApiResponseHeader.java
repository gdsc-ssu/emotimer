package com.gdsc.timerservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApiResponseHeader {
    private int code;
    private String message;

}
