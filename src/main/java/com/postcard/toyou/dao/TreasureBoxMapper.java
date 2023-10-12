package com.postcard.toyou.dao;

import com.postcard.toyou.model.TbPicModel;
import com.postcard.toyou.model.TreasureBoxModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TreasureBoxMapper {
    int registWriting(TreasureBoxModel writing);
    int registPic(TbPicModel tbpModel);

}
