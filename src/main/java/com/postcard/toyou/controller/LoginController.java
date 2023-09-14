package com.postcard.toyou.controller;

import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class LoginController {

    @Autowired
    private LoginService lService;

    @PostMapping("/login")
    public ResponseEntity<ResultModel> login(@RequestBody MemberModel mModel) {
        return lService.login(mModel);
    }

    @PostMapping("snslogin")
    public ResponseEntity<ResultModel> snsLogin(@RequestBody String params, HttpServletRequest request) {
        String btnType = request.getHeader("btnType");
        return lService.snsLogin(btnType, params);
    }

    @GetMapping("snslogin")
    public ResponseEntity<ResultModel> getSnsInfo(HttpServletRequest request) {
        String btnType = request.getHeader("btnType");
        String authHeader = request.getHeader("Authorization");
        String contentHeader = request.getHeader("Content-type");
        return lService.getSnsInfo(btnType, authHeader, contentHeader);
    }

}
