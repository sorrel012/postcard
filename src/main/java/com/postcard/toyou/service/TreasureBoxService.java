package com.postcard.toyou.service;

import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.model.TreasureBoxModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TreasureBoxService {

    ResponseEntity<ResultModel> registWriting(TreasureBoxModel writing, List<String> imageList);
    ResponseEntity<ResultModel> getWritingList(int selectedOption);

}
