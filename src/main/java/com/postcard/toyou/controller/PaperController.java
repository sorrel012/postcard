package com.postcard.toyou.controller;

import com.postcard.toyou.model.PaperModel;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")

@Slf4j
@RestController
public class PaperController {

    @Autowired
    private PaperService pService;

    @PostMapping("/create")
    public ResponseEntity<ResultModel> create(@RequestBody PaperModel pModel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: create ::: PaperModel : {}",  pModel.toString());
        }

        return pService.create(pModel);
    }

    @GetMapping("/mypaperlist")
    public ResponseEntity<ResultModel> getList(@RequestParam String id) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: getList ::: String : {}",  id);
        }

        return pService.getList(id);
    }

    @GetMapping("/paper")
    public ResponseEntity<ResultModel> getPaper(@RequestParam int code) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: getPaper ::: int : {}",  code);
        }

        return pService.getPaper(code);
    }

    @DeleteMapping("/paper")
    public ResponseEntity<ResultModel> deletePaper(@RequestParam int pcc_seq) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: deletePaper ::: int : {}",  pcc_seq);
        }

        return pService.deletePaper(pcc_seq);
    }

}
