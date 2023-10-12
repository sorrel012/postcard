package com.postcard.toyou.service;

import com.postcard.toyou.dao.TreasureBoxMapper;
import com.postcard.toyou.model.PostcardModel;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.model.TreasureBoxModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreasureBoxServiceImpl implements TreasureBoxService {

    private final TreasureBoxMapper tbMapper;

    public TreasureBoxServiceImpl(TreasureBoxMapper tbMapper) {
        this.tbMapper = tbMapper;
    }

    @Override
    public ResponseEntity<ResultModel> registWriting(TreasureBoxModel writing, List<String> imageList) {

        ResultModel rModel = new ResultModel();
//
//        //사진 o
//
//        rModel.setState(true);
//        rModel.setMessage("쪽지 목록을 불러왔습니다.");
//        rModel.setResult(pcList);

        return ResponseEntity.ok(rModel);
    }

}
