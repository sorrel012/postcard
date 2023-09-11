package com.postcard.toyou.service;

import com.postcard.toyou.dao.MemberMapper;
import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private MemberMapper mMapper;

    public ResponseEntity<ResultModel> register(MemberModel mModel) {

        ResultModel rModel = new ResultModel();

        int result = mMapper.register(mModel);
        if(result == 1) {
            rModel.setState(true);
            rModel.setMessage("회원가입에 성공하였습니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage("회원가입에 실패하였습니다.");
        }

        return ResponseEntity.ok(rModel);

    };

    @Override
    public ResponseEntity<Object> selectDupId(String id) {

        ResultModel rModel = new ResultModel();

        List<String> dupId = this.mMapper.selectDupId(id);
        if(dupId.size() == 0) {
            rModel.setState(true);
            rModel.setMessage(id + " 는 사용할 수 있는 아이디입니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage(id + " 는 사용할 수 없는 아이디입니다.");
        }

        return ResponseEntity.ok(rModel);
    }
}
