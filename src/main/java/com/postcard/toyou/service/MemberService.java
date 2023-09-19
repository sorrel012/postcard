package com.postcard.toyou.service;

import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.http.ResponseEntity;

public interface MemberService {

    public ResponseEntity<ResultModel> login(MemberModel mModel);
    public ResponseEntity<ResultModel> snsLogin(String btnType, String params);
    public ResponseEntity<ResultModel> getSnsInfo( String btnType, String authHeader, String contentHeader);
    public ResponseEntity<ResultModel> naverLogout(String params);
    public ResponseEntity<ResultModel> register(MemberModel mModel);
    public ResponseEntity<Object> selectDupId(String id);
    public ResponseEntity<ResultModel> snsRegister(MemberModel mModel);
    public ResponseEntity<Object> updateProfile(MemberModel mModel);
    public ResponseEntity<Object> updatePw(MemberModel mModel);
    public ResponseEntity<Object> deleteAccount(String id);
    ResponseEntity<Object> disconnectKakao(String authHeader);
    ResponseEntity<Object> disconnectNaver(String params);

}
