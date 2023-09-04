package com.postcard.toyou.controller;

import com.postcard.toyou.model.PostcardModel;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.PostcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class PostcardController {

    @Autowired
    private PostcardService pcService;

    @GetMapping("/postcardlist")
    public ResponseEntity<ResultModel> getList(@RequestParam int pcc_seq) {
        return pcService.getList(pcc_seq);
    }

    @PostMapping("/write")
    public ResponseEntity<ResultModel> writePostcard(@RequestBody PostcardModel pmodel) {
        return pcService.writePostcard(pmodel);
    }

    @DeleteMapping("/postcard")
    public ResponseEntity<ResultModel> deletePostcard(@RequestParam int pc_seq) {
        return pcService.deletePostcard(pc_seq);
    }


}
