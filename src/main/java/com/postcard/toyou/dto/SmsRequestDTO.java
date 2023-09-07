package com.postcard.toyou.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SmsRequestDTO {

    private String type;
    private String contentType;
    private String countryCode;
    private String from;
    private String content;
    private List<SmsMessageDTO> messages;

}
