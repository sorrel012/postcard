package com.postcard.toyou.dao;

import com.postcard.toyou.model.TbPicModel;
import com.postcard.toyou.model.TreasureBoxModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TreasureBoxMapper {

    int registWriting(TreasureBoxModel writing);
    int registPic(TbPicModel tbpModel);
    List<TreasureBoxModel> getWritingList(int selectedOption);

}
