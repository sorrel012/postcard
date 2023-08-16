package com.postcard.toyou.controller;

import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class RegisterController {


    @Autowired
    private RegisterService rService;

    @PostMapping ("/register")
    public ResultModel register() {
        return rService.register();
    }

}
