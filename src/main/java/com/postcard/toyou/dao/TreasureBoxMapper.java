package com.postcard.toyou.dao;

import com.postcard.toyou.common.PageCriteria;
import com.postcard.toyou.dto.SearchDTO;
import com.postcard.toyou.model.TbCommentModel;
import com.postcard.toyou.model.TbPicModel;
import com.postcard.toyou.model.TreasureBoxModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TreasureBoxMapper {

    int registPost(TreasureBoxModel tbModel);
    int editPost(TreasureBoxModel tbModel);
    int registPic(TbPicModel tbpModel);
    void deletePostPic(String url);
    List<TreasureBoxModel> getPostList(@Param("search") SearchDTO sDto, @Param("page") PageCriteria criteria);
    int getTotalPostCount();
    int registComment(TbCommentModel tbcModel);
    List<TbCommentModel> getCommentList(int seq);
    int increaseHit(int seq);
    int deletePost(int seq);
    int deleteComment(int seq);
    void deleteReply(int c_seq);
    String getContent(int seq);

}
