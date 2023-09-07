package com.postcard.toyou.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmsResponseDTO {

    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;

}

