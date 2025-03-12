package com.cybertronix.vehiclebooking.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CommonResponse {
    private boolean isSuccess;
    private String message;
    private Instant timestamp;
    private Object dataSet;

    public CommonResponse(boolean isSuccess, String message, Object dataSet) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.timestamp = Instant.now();
        this.dataSet = dataSet;
    }
}
