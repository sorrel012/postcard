package com.postcard.toyou.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.postcard.toyou.dto.SmsMessageDTO;
import com.postcard.toyou.dto.SmsResponseDTO;
import com.postcard.toyou.service.SmsAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class SmsAuthController {

    private SmsAuthService smsAuthService;

    @PostMapping("/auth")
    public String sendSms(@RequestBody SmsMessageDTO messageDto, Model model) throws JsonProcessingException, RestClientException, URISyntaxException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        SmsResponseDTO response = smsAuthService.sendSms(messageDto);
        model.addAttribute("response", response);
        return "result";
    }

}
