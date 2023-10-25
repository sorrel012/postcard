package com.postcard.toyou.dao;

import com.postcard.toyou.common.PageCriteria;
import com.postcard.toyou.model.PaperModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperMapper {

    public int create(PaperModel pModel);
    public int isUnique(int code);
    List<PaperModel> getMyPaperList(@Param("id") String id, @Param("page") PageCriteria criteria);
    int getCnt(int seq);
    List<PaperModel> getPaper(int code);
    int deletePaper(int pccSeq);
}
