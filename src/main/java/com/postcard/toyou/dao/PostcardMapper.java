package com.postcard.toyou.dao;

import com.postcard.toyou.model.PostcardModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostcardMapper {

    List<PostcardModel> getList(int pccSeq);
    int writePostcard(PostcardModel pmodel);

}
