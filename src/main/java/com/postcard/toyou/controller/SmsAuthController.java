package com.postcard.toyou.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.postcard.toyou.dto.SmsMessageDTO;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.SmsAuthService;
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
@RestController
public class SmsAuthController {

    @Autowired
    private SmsAuthService smsAuthService;

    @PostMapping("/auth")
    public ResponseEntity<ResultModel> sendSms(@RequestBody SmsMessageDTO messageDto) throws JsonProcessingException, RestClientException, URISyntaxException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        return smsAuthService.sendSms(messageDto);
    }

}
