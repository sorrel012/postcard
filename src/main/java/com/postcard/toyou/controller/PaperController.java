package com.postcard.toyou.controller;

import com.postcard.toyou.common.PageCriteria;
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
    public ResponseEntity<ResultModel> getMyPaperList(
            @RequestParam String id,
            @RequestParam int pageNo,
            @RequestParam int size) {

        PageCriteria criteria = new PageCriteria();
        criteria.setPageNo(pageNo);
        criteria.setSize(size);

        if ( log.isDebugEnabled() ) {
            log.debug("::: getMyPaperList ::: String : {}",  id);
            log.debug("::: getMyPaperList ::: PageCriteria : {}",  criteria);
        }

        return pService.getMyPaperList(id, criteria);
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
