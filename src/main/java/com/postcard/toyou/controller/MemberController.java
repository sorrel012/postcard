package com.postcard.toyou.controller;

import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class MemberController {

    @Autowired
    private MemberService mService;

    @PostMapping("/login")
    public ResponseEntity<ResultModel> login(@RequestBody MemberModel mModel) {
        return mService.login(mModel);
    }

    @PostMapping("snslogin")
    public ResponseEntity<ResultModel> snsLogin(@RequestBody String params, HttpServletRequest request) {
        String btnType = request.getHeader("btnType");
        return mService.snsLogin(btnType, params);
    }

    @GetMapping("snslogin")
    public ResponseEntity<ResultModel> getSnsInfo(HttpServletRequest request) {
        String btnType = request.getHeader("btnType");
        String authHeader = request.getHeader("Authorization");
        String contentHeader = request.getHeader("Content-type");
        return mService.getSnsInfo(btnType, authHeader, contentHeader);
    }

    @PostMapping ("/register")
    public ResponseEntity<ResultModel> register(@RequestBody MemberModel mModel) {
        return mService.register(mModel);
    }

    @PostMapping ("/snsregister")
    public ResponseEntity<ResultModel> snsRegister(@RequestBody MemberModel mModel) {
        return mService.snsRegister(mModel);
    }

    @PostMapping(value = "/dup")
    public ResponseEntity<Object> selectDupId(@RequestBody MemberModel mModel) {
        return mService.selectDupId(mModel.getId());
    }

    @PutMapping("/updateprofile")
    public ResponseEntity<Object> updateProfile(@RequestBody MemberModel mModel) {
        return mService.updateProfile(mModel);
    }

    @PutMapping("/updatepw")
    public ResponseEntity<Object> updatePw(@RequestBody MemberModel mModel) {
        return mService.updatePw(mModel);
    }

}
