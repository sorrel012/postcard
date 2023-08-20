package com.postcard.toyou.service;

import com.postcard.toyou.model.ResultModel;
import org.springframework.http.ResponseEntity;

public interface PostcardService {
    ResponseEntity<ResultModel> getList(int pccSeq);
}
