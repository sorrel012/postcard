package com.postcard.toyou.service;

import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.model.TreasureBoxModel;
import org.springframework.http.ResponseEntity;

public interface TreasureBoxService {

    ResponseEntity<ResultModel> registWriting(TreasureBoxModel writing);

}
