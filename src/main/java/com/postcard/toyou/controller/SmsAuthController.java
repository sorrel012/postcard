package com.postcard.toyou.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.postcard.toyou.dto.SmsMessageDTO;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.SmsAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@CrossOrigin(origins = "http://localhost:8080")
@Slf4j
@RestController
public class SmsAuthController {

    @Autowired
    private SmsAuthService smsAuthService;

    @PostMapping("/auth")
    public ResponseEntity<ResultModel> sendSms(@RequestBody SmsMessageDTO messageDto) throws JsonProcessingException, RestClientException, URISyntaxException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {

        if ( log.isDebugEnabled() ) {
            log.debug("::: sendSms ::: SmsMessageDTO : {}",  messageDto.toString());
        }

        String message = "인증번호를 입력해 주세요.\r\n"+ messageDto.getContent();
        messageDto.setContent(message);

        return smsAuthService.sendSms(messageDto);
    }

}
