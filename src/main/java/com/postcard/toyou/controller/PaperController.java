package com.postcard.toyou.controller;

import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.PaperModel;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.LoginService;
import com.postcard.toyou.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Paper;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class PaperController {

    @Autowired
    private PaperService pService;

    @PostMapping("/create")
    public ResponseEntity<ResultModel> create(@RequestBody PaperModel pModel) {

        return pService.create(pModel);
    }

}
