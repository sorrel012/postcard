package com.postcard.toyou.controller;

import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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

    @PostMapping("naverlogin")
    public ResponseEntity<ResultModel> naverLogin(@RequestBody String params) {
        return lService.naverLogin(params);
    }

    @GetMapping("naverlogin")
    public ResponseEntity<ResultModel> getNaverUser(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return lService.getNaverUser(header);
    }

}
