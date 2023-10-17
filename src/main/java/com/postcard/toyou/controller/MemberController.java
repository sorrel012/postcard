package com.postcard.toyou.controller;

import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:8080")
@Slf4j
@RestController
public class MemberController {

    @Autowired
    private MemberService mService;

    @PostMapping("/login")
    public ResponseEntity<ResultModel> login(@RequestBody MemberModel mModel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: login ::: MemberModel : {}",  mModel.toString());
        }

        return mService.login(mModel);
    }

    @PostMapping("/snslogin")
    public ResponseEntity<ResultModel> snsLogin(@RequestBody String params, HttpServletRequest request) {
        String btnType = request.getHeader("btnType");

        if ( log.isDebugEnabled() ) {
            log.debug("::: snsLogin ::: String : {}",  params);
        }

        return mService.snsLogin(btnType, params);
    }

    @GetMapping("/snslogin")
    public ResponseEntity<ResultModel> getSnsInfo(HttpServletRequest request) {
        String btnType = request.getHeader("btnType");
        String authHeader = request.getHeader("Authorization");
        String contentHeader = request.getHeader("Content-type");
        return mService.getSnsInfo(btnType, authHeader, contentHeader);
    }

    @PostMapping("/snslogout")
    public ResponseEntity<ResultModel> naverLogout(@RequestBody String params) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: naverLogout ::: String : {}",  params);
        }

        return mService.naverLogout(params);
    }

    @PostMapping ("/register")
    public ResponseEntity<ResultModel> register(@RequestBody MemberModel mModel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: register ::: MemberModel : {}",  mModel.toString());
        }

        return mService.register(mModel);
    }

    @PostMapping ("/snsregister")
    public ResponseEntity<ResultModel> snsRegister(@RequestBody MemberModel mModel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: snsRegister ::: MemberModel : {}",  mModel.toString());
        }

        return mService.snsRegister(mModel);
    }

    @PostMapping(value = "/checkrejoin")
    public ResponseEntity<Object> checkRejoin(@RequestBody MemberModel mModel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: checkRejoin ::: MemberModel : {}",  mModel.toString());
        }

        return mService.checkRejoin(mModel.getId());
    }

    @PostMapping(value = "/dup-id")
    public ResponseEntity<Object> selectDupId(@RequestBody MemberModel mModel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: selectDupId ::: MemberModel : {}",  mModel.toString());
        }

        return mService.selectDupId(mModel.getId());
    }

    @PostMapping(value = "/dup-name")
    public ResponseEntity<Object> selectDupName(@RequestBody MemberModel mModel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: selectDupName ::: MemberModel : {}",  mModel.toString());
        }

        return mService.selectDupName(mModel.getName());
    }

    @PutMapping("/updateprofile")
    public ResponseEntity<Object> updateProfile(@RequestBody MemberModel mModel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: updateProfile ::: MemberModel : {}",  mModel.toString());
        }

        return mService.updateProfile(mModel);
    }

    @PutMapping("/updatepw")
    public ResponseEntity<Object> updatePw(@RequestBody MemberModel mModel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: updatePw ::: MemberModel : {}",  mModel.toString());
        }

        return mService.updatePw(mModel);
    }

    @DeleteMapping("/deleteaccount")
    public ResponseEntity<Object> deleteAccount(@RequestParam String id) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: deleteAccount ::: String : {}",  id);
        }

        return mService.deleteAccount(id);
    }

    @PostMapping("/disconnectKakao")
    public ResponseEntity<Object> disconnectKakao(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return mService.disconnectKakao(authHeader);
    }

    @PostMapping("/disconnectNaver")
    public ResponseEntity<Object> disconnectNaver(@RequestBody String params) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: disconnectNaver ::: String : {}",  params);
        }

        return mService.disconnectNaver(params);
    }

    @PostMapping("/disconnectGoogle")
    public ResponseEntity<Object> disconnectGoogle(@RequestBody String token) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: disconnectGoogle ::: String : {}",  token);
        }

        return mService.disconnectGoogle(token);
    }

    @PostMapping("/getName")
    public ResponseEntity<Object> getName(@RequestBody String id) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: getName ::: String : {}",  id);
        }

        return mService.getName(id);
    }

}
