package com.postcard.toyou.dao;

import com.postcard.toyou.model.PaperModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaperMapper {

    public int create(PaperModel pModel);
    public int isUnique(int code);
    List<PaperModel> getList(int mSeq);
    int getCnt(int seq);
    List<PaperModel> getPaper(int code);
}
