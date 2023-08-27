package com.postcard.toyou.controller;

import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.PostcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class PostcardController {

    @Autowired
    private PostcardService pcService;

    @GetMapping("/postcardlist")
    public ResponseEntity<ResultModel> getList(@RequestParam int pcc_seq) {

        System.out.println(pcc_seq);

        return pcService.getList(pcc_seq);
    }

}
