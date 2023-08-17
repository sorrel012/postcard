package com.postcard.toyou.dao;

import com.postcard.toyou.model.MemberModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.ui.Model;

@Mapper
public interface MemberMapper {

    public int register(MemberModel mModel);
    public MemberModel login(MemberModel mModel);

}
