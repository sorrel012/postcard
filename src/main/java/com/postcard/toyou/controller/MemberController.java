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

    @PostMapping("snslogout")
    public ResponseEntity<ResultModel> naverLogout(@RequestBody String params) {
        return mService.naverLogout(params);
    }

    @PostMapping ("/register")
    public ResponseEntity<ResultModel> register(@RequestBody MemberModel mModel) {
        return mService.register(mModel);
    }

    @PostMapping ("/snsregister")
    public ResponseEntity<ResultModel> snsRegister(@RequestBody MemberModel mModel) {
        return mService.snsRegister(mModel);
    }

    @PostMapping(value = "/checkrejoin")
    public ResponseEntity<Object> checkRejoin(@RequestBody MemberModel mModel) {
        return mService.checkRejoin(mModel.getId());
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

    @DeleteMapping("deleteaccount")
    public ResponseEntity<Object> deleteAccount(@RequestParam String id) {
        return mService.deleteAccount(id);
    }

    @PostMapping("disconnectKakao")
    public ResponseEntity<Object> disconnectKakao(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return mService.disconnectKakao(authHeader);
    }

    @PostMapping("disconnectNaver")
    public ResponseEntity<Object> disconnectNaver(@RequestBody String params) {
        return mService.disconnectNaver(params);
    }

    @PostMapping("disconnectGoogle")
    public ResponseEntity<Object> disconnectGoogle(@RequestBody String token) {
        return mService.disconnectGoogle(token);
    }

}
