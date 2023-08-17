package com.postcard.toyou.service;

import com.postcard.toyou.dao.MemberMapper;
import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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


}
