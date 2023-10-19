package com.postcard.toyou.dao;

import com.postcard.toyou.model.TbCommentModel;
import com.postcard.toyou.model.TbPicModel;
import com.postcard.toyou.model.TreasureBoxModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TreasureBoxMapper {

    int registPost(TreasureBoxModel tbModel);
    int editPost(TreasureBoxModel tbModel);
    int registPic(TbPicModel tbpModel);
    void deletePostPic(int b_seq);
    List<TreasureBoxModel> getPostList(int selectedOption);
    int registComment(TbCommentModel tbcModel);
    List<TbCommentModel> getCommentList(int seq);
    int increaseHit(int seq);
    int deletePost(int seq);
    int deleteComment(int seq);
    void deleteReply(int c_seq);
    String getContent(int seq);
}
