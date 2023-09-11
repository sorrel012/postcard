package com.postcard.toyou.controller;

import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class RegisterController {

    @Autowired
    private RegisterService rService;

    @PostMapping ("/register")
    public ResponseEntity<ResultModel> register(@RequestBody MemberModel mModel) {
        return rService.register(mModel);
    }

    @PostMapping(value = "/dup")
    public ResponseEntity<Object> selectDupId(@RequestBody MemberModel mModel) {
        return rService.selectDupId(mModel.getId());
    }

}
