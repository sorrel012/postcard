package com.postcard.toyou.service;

import com.postcard.toyou.model.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private MemberMapper mMapper;

    public ResultModel register() {

        ResultModel rModel = new ResultModel();

    };


}
