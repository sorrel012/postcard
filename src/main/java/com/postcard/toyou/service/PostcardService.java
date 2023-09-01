package com.postcard.toyou.service;

import com.postcard.toyou.model.PostcardModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.http.ResponseEntity;

public interface PostcardService {
    ResponseEntity<ResultModel> getList(int pccSeq);
    ResponseEntity<ResultModel> writePostcard(PostcardModel pmodel);
}
