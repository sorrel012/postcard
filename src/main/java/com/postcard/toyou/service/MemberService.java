package com.postcard.toyou.service;

import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    ResponseEntity<ResultModel> login(MemberModel mModel);
    ResponseEntity<ResultModel> snsLogin(String btnType, String params);
    ResponseEntity<ResultModel> getSnsInfo( String btnType, String authHeader, String contentHeader);
    ResponseEntity<ResultModel> naverLogout(String params);
    ResponseEntity<ResultModel> register(MemberModel mModel);
    ResponseEntity<Object> checkRejoin(String id);
    ResponseEntity<Object> selectDupId(String id);
    ResponseEntity<Object> selectDupName(String name);
    ResponseEntity<ResultModel> snsRegister(MemberModel mModel);
    ResponseEntity<Object> updateProfile(MemberModel mModel);
    ResponseEntity<Object> updatePw(MemberModel mModel);
    ResponseEntity<Object> deleteAccount(String id);
    ResponseEntity<Object> disconnectKakao(String authHeader);
    ResponseEntity<Object> disconnectNaver(String params);
    ResponseEntity<Object> disconnectGoogle(String token);

}
