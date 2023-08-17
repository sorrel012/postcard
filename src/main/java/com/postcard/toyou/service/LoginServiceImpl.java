package com.postcard.toyou.service;

import com.postcard.toyou.dao.MemberMapper;
import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private MemberMapper mMapper;

    @Override
    public ResponseEntity<ResultModel> login(MemberModel mModel) {

        ResultModel rModel = new ResultModel();

        mModel = mMapper.login(mModel);
        if(mModel.getName() != null) {
            rModel.setState(true);
            rModel.setResult(mModel);
            rModel.setMessage(mModel.getName()+"님 반갑습니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage("아이디 비밀번호가 일치하지 않습니다.");
        }

        return ResponseEntity.ok(rModel);
    }

}
