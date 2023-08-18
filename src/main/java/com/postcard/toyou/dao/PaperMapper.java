package com.postcard.toyou.dao;

import com.postcard.toyou.model.PaperModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaperMapper {

    public int create(PaperModel pModel);
    public int isUnique(int code);

}
