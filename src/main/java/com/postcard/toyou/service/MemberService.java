package com.postcard.toyou.service;

import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    ResponseEntity<ResultModel> login(MemberModel mModel);
    ResponseEntity<ResultModel> snsLogin(String btnType, String params);
    ResponseEntity<ResultModel> getSnsInfo( String btnType, String authHeader, String contentHeader);
    ResponseEntity<ResultModel> naverLogout(String params);
    public ResponseEntity<ResultModel> register(MemberModel mModel);
    public ResponseEntity<Object> checkRejoin(String id);
    public ResponseEntity<Object> selectDupId(String id);
    public ResponseEntity<ResultModel> snsRegister(MemberModel mModel);
    public ResponseEntity<Object> updateProfile(MemberModel mModel);
    public ResponseEntity<Object> updatePw(MemberModel mModel);
    public ResponseEntity<Object> deleteAccount(String id);
    public ResponseEntity<Object> disconnectKakao(String authHeader);
    public ResponseEntity<Object> disconnectNaver(String params);
    public ResponseEntity<Object> disconnectGoogle(String token);

}
