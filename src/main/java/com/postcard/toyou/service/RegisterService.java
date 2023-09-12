package com.postcard.toyou.service;

import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.http.ResponseEntity;

public interface RegisterService {

    public ResponseEntity<ResultModel> register(MemberModel mModel);
    ResponseEntity<Object> selectDupId(String id);
    ResponseEntity<ResultModel> snsRegister(MemberModel mModel);
}
