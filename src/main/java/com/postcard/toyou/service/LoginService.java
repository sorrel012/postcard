package com.postcard.toyou.service;

import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    public ResponseEntity<ResultModel> login(MemberModel mModel);
    ResponseEntity<ResultModel> snsLogin(String btnType, String params);
    ResponseEntity<ResultModel> getSnsInfo( String btnType, String authHeader, String contentHeader);

}
