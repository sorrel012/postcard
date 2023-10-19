package com.postcard.toyou.service;

import com.postcard.toyou.dto.SearchDTO;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.model.TbCommentModel;
import com.postcard.toyou.model.TreasureBoxModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TreasureBoxService {

    ResponseEntity<ResultModel> registPost(TreasureBoxModel post, List<String> imageList);
    ResponseEntity<ResultModel> editPost(TreasureBoxModel tbModel, List<String> imageList);
    ResponseEntity<ResultModel> getPostList(SearchDTO sDto);
    ResponseEntity<ResultModel> registComment(TbCommentModel tbcModel);
    ResponseEntity<ResultModel> getCommentList(int seq);
    ResponseEntity<ResultModel> increaseHit(int seq);
    ResponseEntity<ResultModel> deletePost(int seq);

}
