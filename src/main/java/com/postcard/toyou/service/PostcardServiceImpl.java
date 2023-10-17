package com.postcard.toyou.service;

import com.postcard.toyou.dao.PostcardMapper;
import com.postcard.toyou.model.PostcardModel;
import com.postcard.toyou.model.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostcardServiceImpl implements PostcardService {

    @Autowired
    private PostcardMapper pcMapper;

    @Override
    public ResponseEntity<ResultModel> getList(int pccSeq) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: getList ::: int : {}",  pccSeq);
        }
        
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

    @Override
    public ResponseEntity<ResultModel> writePostcard(PostcardModel pmodel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: writePostcard ::: PostcardModel : {}",  pmodel.toString());
        }

        ResultModel rModel = new ResultModel();

        int result = pcMapper.writePostcard(pmodel);

        if(result > 0) {
            rModel.setState(true);
            rModel.setMessage("쪽지를 남겼습니다.");
            rModel.setResult(true);
        } else {
            rModel.setState(false);
            rModel.setMessage("쪽지를 남기지 못했습니다.");
            rModel.setResult(false);
        }

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> deletePostcard(int pcSeq) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: deletePostcard ::: int : {}",  pcSeq);
        }

        ResultModel rModel = new ResultModel();

        int result = pcMapper.deletePostcard(pcSeq);

        if(result > 0) {
            rModel.setState(true);
            rModel.setMessage("쪽지를 삭제했습니다.");
            rModel.setResult(true);
        } else {
            rModel.setState(false);
            rModel.setMessage("쪽지를 삭제하지 못했습니다.");
            rModel.setResult(false);
        }

        return ResponseEntity.ok(rModel);
    }

}
