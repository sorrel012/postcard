package com.postcard.toyou.controller;

import com.postcard.toyou.model.PaperModel;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")


@RestController
public class PaperController {

    @Autowired
    private PaperService pService;

    @PostMapping("/create")
    public ResponseEntity<ResultModel> create(@RequestBody PaperModel pModel) {
        return pService.create(pModel);
    }

    @GetMapping("/mypaperlist")
    public ResponseEntity<ResultModel> getList(@RequestParam int m_seq) {
        return pService.getList(m_seq);
    }

    @GetMapping("/paper")
    public ResponseEntity<ResultModel> getPaper(@RequestParam int code) {
        return pService.getPaper(code);
    }

}
