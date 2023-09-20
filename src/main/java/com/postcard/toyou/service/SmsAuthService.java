package com.postcard.toyou.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.postcard.toyou.dto.SmsMessageDTO;
import com.postcard.toyou.model.ResultModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface SmsAuthService {

    String makeSignature(Long time) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;

    ResponseEntity<ResultModel> sendSms(SmsMessageDTO messageDto) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException;

}
