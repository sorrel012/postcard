package com.postcard.toyou.dto;

import lombok.Data;

@Data
public class NaverTokenDTO {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;
    private String error;
    private String error_description;

}
