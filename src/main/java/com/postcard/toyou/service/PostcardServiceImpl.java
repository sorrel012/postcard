package com.postcard.toyou.service;

import com.postcard.toyou.dao.PostcardMapper;
import com.postcard.toyou.model.PostcardModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostcardServiceImpl implements PostcardService {

    @Autowired
    private PostcardMapper pcMapper;

    @Override
    public ResponseEntity<ResultModel> getList(int pccSeq) {
        
        ResultModel rModel = new ResultModel();

        List<PostcardModel> pcList = pcMapper.getList(pccSeq);
        for(PostcardModel pc : pcList) {
            String regdate = pc.getRegDate();
            regdate = regdate.split(" ")[0];
            pc.setRegDate(regdate);
        }

        rModel.setState(true);
        rModel.setMessage("쪽지 목록을 불러왔습니다.");
        rModel.setResult(pcList);

        return ResponseEntity.ok(rModel);
    }
}
