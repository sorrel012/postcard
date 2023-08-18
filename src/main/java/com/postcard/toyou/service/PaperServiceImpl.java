package com.postcard.toyou.service;

import com.postcard.toyou.dao.MemberMapper;
import com.postcard.toyou.dao.PaperMapper;
import com.postcard.toyou.model.PaperModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Paper;
import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperMapper pMapper;

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

        ResultModel rModel = new ResultModel();
        int result = pMapper.create(pModel);
        if (result == 1) {
            rModel.setState(true);
            rModel.setMessage("도화지가 생성되었습니다.");
            rModel.setResult(code);
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
    public ResponseEntity<ResultModel> getList(int mSeq) {

        ResultModel rModel = new ResultModel();

        List<PaperModel> plist = pMapper.getList(mSeq);
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
}
