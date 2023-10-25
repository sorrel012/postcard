package com.postcard.toyou.service;

import com.postcard.toyou.common.PageCriteria;
import com.postcard.toyou.dao.PaperMapper;
import com.postcard.toyou.dao.PostcardMapper;
import com.postcard.toyou.model.PaperModel;
import com.postcard.toyou.model.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperMapper pMapper;

    @Autowired
    private PostcardMapper pcMapper;

    @Override
    public ResponseEntity<ResultModel> create(PaperModel pModel) {

        // 고유코드인지 확인
        int code = makeCode();
        int uniqueCode = pMapper.isUnique(code);

        // 고유코드일 때까지 난수 생성 및 확인
        while(uniqueCode != 0) {
            code = makeCode();
            uniqueCode = pMapper.isUnique(code);
        }

        pModel.setCode(code);

        if ( log.isDebugEnabled() ) {
            log.debug("::: create ::: PaperModel : {}",  pModel.toString());
        }

        ResultModel rModel = new ResultModel();
        Map<String, Integer> returnResult = new HashMap<>();

        returnResult.put("code", code);

        int result = pMapper.create(pModel);
        returnResult.put("pcc_seq", result);

        if (result > 0) {
            rModel.setState(true);
            rModel.setMessage("도화지가 생성되었습니다.");
            rModel.setResult(returnResult);
        } else {
            rModel.setState(false);
            rModel.setMessage("도화지를 만들지 못하였습니다.");
        }

        return ResponseEntity.ok(rModel);
    }

    private int makeCode() {

        // 6자리의 난수 생성
        int min = 100000;
        int max = 999999;
        int code = (int) (Math.random() * (max - min + 1)) + min;

        return code;
    }

    @Override
    public ResponseEntity<ResultModel> getMyPaperList(String id, PageCriteria criteria) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: getMyPaperList ::: String : {}",  id);
            log.debug("::: getMyPaperList ::: PageCriteria : {}",  criteria);
        }

        ResultModel rModel = new ResultModel();

        List<PaperModel> plist = pMapper.getMyPaperList(id, criteria);
        for(PaperModel p : plist) {

            int seq = p.getPcc_seq();
            int cnt = pMapper.getCnt(seq);
            p.setPcCnt(cnt);

            String regdate = p.getRegdate();
            regdate = regdate.split(" ")[0];
            p.setRegdate(regdate);

        }

        rModel.setState(true);
        rModel.setMessage("도화지 목록을 불러왔습니다.");
        rModel.setResult(plist);

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> getPaper(int code) {

        ResultModel rModel = new ResultModel();

        PaperModel paper = pMapper.getPaper(code);
        String regdate = paper.getRegdate();
        regdate = regdate.split(" ")[0];
        paper.setRegdate(regdate);

        if ( log.isDebugEnabled() ) {
            log.debug("::: getPaper ::: int : {}",  code);
        }

        rModel.setState(true);
        rModel.setMessage("도화지를 불러왔습니다.");
        rModel.setResult(paper);

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> deletePaper(int pccSeq) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: deletePaper ::: int : {}",  pccSeq);
        }

        ResultModel rModel = new ResultModel();

        int result1 = pMapper.deletePaper(pccSeq);

        if(result1 > 0) {
            int result2 = pcMapper.deleteAllPostcards(pccSeq);

            if (result2 > 0) {
                rModel.setState(true);
                rModel.setMessage("도화지를 삭제했습니다.");
                rModel.setResult(true);

            } else {
                rModel.setState(false);
                rModel.setMessage("도화지를 삭제하지 못했습니다.");
                rModel.setResult(false);
            }

        } else {
            rModel.setState(false);
            rModel.setMessage("도화지를 삭제하지 못했습니다.");
            rModel.setResult(false);
        }

        return ResponseEntity.ok(rModel);
    }

}
