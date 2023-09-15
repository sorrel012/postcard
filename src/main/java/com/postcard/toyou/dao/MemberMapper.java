package com.postcard.toyou.dao;

import com.postcard.toyou.model.MemberModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.ui.Model;

import java.util.List;

@Mapper
public interface MemberMapper {

    public int register(MemberModel mModel);
    public MemberModel login(MemberModel mModel);
    public List<String> selectDupId(String id);
    public int snsRegister(MemberModel mModel);
    public int updateProfile(MemberModel mModel);
    int updatePw(MemberModel mModel);
    int deleteAccount(String id);

}
