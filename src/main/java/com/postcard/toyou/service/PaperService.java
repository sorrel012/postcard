package com.postcard.toyou.service;

import com.postcard.toyou.common.PageCriteria;
import com.postcard.toyou.model.PaperModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.http.ResponseEntity;

public interface PaperService {

    ResponseEntity<ResultModel> create(PaperModel pModel);
    ResponseEntity<ResultModel> getMyPaperList(String id, PageCriteria criteria);
    ResponseEntity<ResultModel> getPaper(int code);
    ResponseEntity<ResultModel> deletePaper(int pccSeq);

}
