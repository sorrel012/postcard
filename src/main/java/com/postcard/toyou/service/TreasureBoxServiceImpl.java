package com.postcard.toyou.service;

import com.postcard.toyou.common.PageCriteria;
import com.postcard.toyou.dao.MemberMapper;
import com.postcard.toyou.dao.TreasureBoxMapper;
import com.postcard.toyou.dto.SearchDTO;
import com.postcard.toyou.model.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TreasureBoxServiceImpl implements TreasureBoxService {

    private final TreasureBoxMapper tbMapper;
    private final MemberMapper mMapper;
    private final S3FileUploadService s3Service;

    public TreasureBoxServiceImpl(TreasureBoxMapper tbMapper, MemberMapper mMapper, S3FileUploadService s3UploadService) {
        this.tbMapper = tbMapper;
        this.s3Service = s3UploadService;
        this.mMapper = mMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<ResultModel> registPost(TreasureBoxModel tbModel, List<String> imageList) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: registPost ::: TreasureBoxModel : {}",  tbModel.toString());
            log.debug("::: registPost ::: List<String> : {}",  imageList.toString());
        }

        ResultModel rModel = new ResultModel();
        
        //게시글 DB 저장
        int postResult = tbMapper.registPost(tbModel);
        
        //본문 내용 저장 성공 && 이미지를 업로드 한 적 있는지 확인
        int picResult = 1;
        if(postResult>0 && !imageList.isEmpty()){

            //게시글 이미지 추출
            List<String> contentImages = getContentImg(tbModel.getContent());

            //업로드 후 삭제한 이미지 추출
            List<String> deletedImg = new ArrayList<>(imageList);
            deletedImg.removeAll(contentImages);

            //AWS S3에서 이미지 삭제
            s3Service.deleteImage(deletedImg);

            //게시글 이미지 DB 저장
            for(String url : contentImages) {
                url = s3Service.decodeUrl(url);
                String imgName = s3Service.extractFileName(url).split("@")[1];

                TbPicModel tbpModel = new TbPicModel();
                tbpModel.setB_seq(postResult);
                tbpModel.setPic_url(url);
                tbpModel.setPic_name(imgName);

                if ( log.isDebugEnabled() ) {
                    log.debug("::: registPost ::: TbPicModel : {}",  tbpModel.toString());
                }

                picResult = tbMapper.registPic(tbpModel);
                if(picResult != 1) {
                    throw new RuntimeException("Image saving failed");
                }

            }

        }

        if (postResult>0 && picResult==1) {
            rModel.setState(true);
            rModel.setMessage("게시글이 작성되었습니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage("게시글 작성에 실패하였습니다.");
        }

        return ResponseEntity.ok(rModel);
    }

    @Override
    @Transactional
    public ResponseEntity<ResultModel> editPost(TreasureBoxModel tbModel, List<String> imageList) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: editPost ::: TreasureBoxModel : {}",  tbModel.toString());
            log.debug("::: editPost ::: List<String> : {}",  imageList.toString());
        }

        ResultModel rModel = new ResultModel();
        
        //수정하면서 삭제된 사진 확인
        String originContent = tbMapper.getContent(tbModel.getB_seq());
        List<String> originContentImg = getContentImg(originContent);           //기존 본문에 있던 사진들
        List<String> newContentImg = getContentImg(tbModel.getContent());       //새로 작성한 본문에 있는 사진들

        List<String> editedImg = new ArrayList<>(originContentImg); //삭제된 사진들
        editedImg.removeAll(newContentImg);

        //S3에서 삭제
        s3Service.deleteImage(editedImg);

        //DB에서 삭제
        for(String img : editedImg) {
            String decodedUrl = s3Service.decodeUrl(img);
            tbMapper.deletePostPic(decodedUrl);
        }

        //본문 DB 수정
        int postResult = tbMapper.editPost(tbModel);

        //본문 내용 수정 성공 && 이미지를 업로드 한 적 있는지 확인
        int picResult = 1;
        if(postResult>0 && !imageList.isEmpty()){

            //업로드 후 삭제한 이미지 추출
            List<String> deletedImg = new ArrayList<>(imageList);
            deletedImg.removeAll(newContentImg);

            //AWS S3에서 이미지 삭제
            s3Service.deleteImage(deletedImg);

            //새로운 게시글 이미지 DB 저장
            List<String> retainedImg = new ArrayList<>(originContentImg);
            retainedImg.retainAll(newContentImg);

            List<String> newImgOnly = new ArrayList<>(newContentImg);
            newImgOnly.removeAll(retainedImg);

            System.out.println(newImgOnly);

            for(String url : newImgOnly) {
                url = s3Service.decodeUrl(url);
                String imgName = s3Service.extractFileName(url).split("@")[1];

                TbPicModel tbpModel = new TbPicModel();
                tbpModel.setB_seq(tbModel.getB_seq());
                tbpModel.setPic_url(url);
                tbpModel.setPic_name(imgName);

                if ( log.isDebugEnabled() ) {
                    log.debug("::: editPost ::: TbPicModel : {}",  tbpModel.toString());
                }

                picResult = tbMapper.registPic(tbpModel);
                if(picResult != 1) {
                    throw new RuntimeException("Image saving failed");
                }

            }
        }

        if (postResult>0 && picResult==1) {
            rModel.setState(true);
            rModel.setMessage("게시글이 수정되었습니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage("게시글 수정에 실패하였습니다.");
        }

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> registComment(TbCommentModel tbcModel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: registComment ::: TbCommentModel : {}",  tbcModel.toString());
        }

        ResultModel rModel = new ResultModel();
        int result = tbMapper.registComment(tbcModel);
        if (result == 1) {
            rModel.setState(true);
            rModel.setMessage("댓글이 등록되었습니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage("댓글을 등록하지 못했습니다.");
        }

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> getPostList(SearchDTO sDto, PageCriteria criteria) {


        if ( log.isDebugEnabled() ) {
            log.debug("::: searchPost ::: SearchDTO : {}",  sDto.toString());
            log.debug("::: searchPost ::: PageCriteria : {}",  criteria.toString());
        }

        ResultModel rModel = new ResultModel();

        List<TreasureBoxModel> wList = tbMapper.getPostList(sDto, criteria);
        for(TreasureBoxModel tb : wList) {

            String regdate = tb.getRegdate();
            regdate = regdate.split(" ")[0];
            tb.setRegdate(regdate);

            tb.setName(mMapper.getName(tb.getM_id()));

        }

        int totalPostCount = tbMapper.getTotalPostCount(sDto);

        rModel.setState(true);
        rModel.setMessage("게시글 목록을 불러왔습니다.");
        rModel.setResult(wList);
        rModel.setTotalRow(totalPostCount);

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> getCommentList(int seq) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: getCommentList ::: int : {}",  seq);
        }

        ResultModel rModel = new ResultModel();

        List<TbCommentModel> wList = tbMapper.getCommentList(seq);
        for(TbCommentModel c : wList) {

            String regdate = c.getRegdate();
            regdate = regdate.split(" ")[0];
            c.setRegdate(regdate);

            String name = mMapper.getName(c.getM_id());
            c.setName(name);

            String writer = String.format("%s(%s***)", name, c.getM_id());
            c.setWriter(writer);

        }

        rModel.setState(true);
        rModel.setMessage("댓글 목록을 불러왔습니다.");
        rModel.setResult(wList);

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> increaseHit(int seq) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: increaseHit ::: int : {}", seq);
        }

        ResultModel rModel = new ResultModel();
        int result = tbMapper.increaseHit(seq);
        if (result == 1) {
            rModel.setState(true);
            rModel.setMessage("조회수가 증가하였습니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage("조회수 증가에 실패하였습니다.");
        }

        return ResponseEntity.ok(rModel);
    }

    @Transactional
    @Override
    public ResponseEntity<ResultModel> deletePost(int seq) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: deletePost ::: int : {}",  seq);
        }

        ResultModel rModel = new ResultModel();

        //게시글 삭제
        int result1 = tbMapper.deletePost(seq);

        String content = tbMapper.getContent(seq);
        List<String> contentImg = getContentImg(content);

        //S3 서버에서 사진 삭제
        s3Service.deleteImage(contentImg);

        //DB에서 사진 삭제
        for(String url : contentImg) {
            String decodedUrl = s3Service.decodeUrl(url);
            tbMapper.deletePostPic(decodedUrl);
        }

        if(result1 > 0) {

            //답글 삭제를 위해 댓글list먼저 select
            List<TbCommentModel> comments = tbMapper.getCommentList(seq);

            //댓글 삭제
            tbMapper.deleteComment(seq);

            //답글 삭제
            for (TbCommentModel comment : comments) {
                tbMapper.deleteReply(comment.getC_seq());
            }

            rModel.setState(true);
            rModel.setMessage("게시글을 삭제했습니다.");
            rModel.setResult(true);

        } else {
            rModel.setState(false);
            rModel.setMessage("게시글을 삭제하지 못했습니다.");
            rModel.setResult(false);
        }

        return ResponseEntity.ok(rModel);
    }

    public List<String> getContentImg(String content) {
        List<String> contentImages = new ArrayList<>();

        Document doc = Jsoup.parse(content);
        Elements imageElements = doc.select("img");

        for (Element imageElement : imageElements) {
            String imageUrl = imageElement.attr("src");
            contentImages.add(imageUrl);
        }
        return contentImages;
    }

    @Override
    public ResponseEntity<ResultModel> getMyPostList(String id, PageCriteria criteria) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: getPostList ::: String : {}",  id);
            log.debug("::: getPostList ::: PageCriteria : {}",  criteria);
        }

        ResultModel rModel = new ResultModel();

        List<TreasureBoxModel> plist = tbMapper.getMyPostList(id, criteria);
        for(TreasureBoxModel tb : plist) {

            int seq = tb.getB_seq();
            int cnt = tbMapper.getCommmentCnt(seq);
            tb.setCommentCnt(cnt);

            String regdate = tb.getRegdate();
            regdate = regdate.split(" ")[0];
            tb.setRegdate(regdate);

        }

        rModel.setState(true);
        rModel.setMessage("게시글 목록을 불러왔습니다.");
        rModel.setResult(plist);

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> getMyCommentList(String id, PageCriteria criteria) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: getMyCommentList ::: String : {}",  id);
            log.debug("::: getMyCommentList ::: PageCriteria : {}",  criteria);
        }

        ResultModel rModel = new ResultModel();

        List<TbCommentModel> clist = tbMapper.getMyCommentList(id, criteria);
        for(TbCommentModel c : clist) {

            int seq = c.getB_seq();
//            int cnt = tbMapper.getCommmentCnt(seq);
//            c.setCommentCnt(cnt);

            String regdate = c.getRegdate();
            regdate = regdate.split(" ")[0];
            c.setRegdate(regdate);

        }
        rModel.setState(true);
        rModel.setMessage("댓글 목록을 불러왔습니다.");
        rModel.setResult(clist);

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> getPost(int seq) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: getPost ::: int : {}",  seq);
        }

        ResultModel rModel = new ResultModel();

        TreasureBoxModel post = tbMapper.getPost(seq);

        String regdate = post.getRegdate();
        regdate = regdate.split(" ")[0];
        post.setRegdate(regdate);
        post.setName(mMapper.getName(post.getM_id()));

        rModel.setState(true);
        rModel.setMessage("게시글을 불러왔습니다.");
        rModel.setResult(post);

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> updateComment(TbCommentModel tbcModel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: updateComment ::: TbCommentModel : {}",  tbcModel);
        }
        
        ResultModel rModel = new ResultModel();

        int result = tbMapper.updateComment(tbcModel);

        if(result > 0) {
            rModel.setState(true);
            rModel.setMessage("댓글을 수정했습니다.");
            rModel.setResult(true);
        } else {
            rModel.setState(false);
            rModel.setMessage("댓글을 수정하지 못했습니다.");
            rModel.setResult(false);
        }

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> deleteComment(int seq) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: deleteComment ::: int : {}",  seq);
        }

        ResultModel rModel = new ResultModel();

        int result = tbMapper.deleteComment(seq);

        if(result > 0) {
            rModel.setState(true);
            rModel.setMessage("댓글을 삭제했습니다.");
            rModel.setResult(true);

        } else {
            rModel.setState(false);
            rModel.setMessage("댓글을 삭제하지 못했습니다.");
            rModel.setResult(false);
        }

        return ResponseEntity.ok(rModel);
    }
}
