package com.postcard.toyou.service;

import com.postcard.toyou.dao.MemberMapper;
import com.postcard.toyou.dao.TreasureBoxMapper;
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
    public ResponseEntity<ResultModel> registPost(TreasureBoxModel post, List<String> imageList) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: registPost ::: TreasureBoxModel : {}",  post.toString());
            log.debug("::: registPost ::: List<String> : {}",  imageList.toString());
        }

        ResultModel rModel = new ResultModel();
        
        //게시글 DB 저장
        int postResult = tbMapper.registPost(post);
        
        //본문 내용 저장 성공 && 이미지를 업로드 한 적 있는지 확인
        int picResult = 1;
        if(postResult>0 && !imageList.isEmpty()){

            //게시글 이미지 추출
            List<String> contentImages = new ArrayList<>();
            Document doc = Jsoup.parse(post.getContent());
            Elements imageElements = doc.select("img");

            for (Element imageElement : imageElements) {
                String imageUrl = imageElement.attr("src");
                contentImages.add(imageUrl);
            }

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
    public ResponseEntity<ResultModel> getPostList(int selectedOption) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: getPostList ::: int : {}",  selectedOption);
        }

        ResultModel rModel = new ResultModel();

        List<TreasureBoxModel> wList = tbMapper.getPostList(selectedOption);
        for(TreasureBoxModel tb : wList) {

            String regdate = tb.getRegdate();
            regdate = regdate.split(" ")[0];
            tb.setRegdate(regdate);

            tb.setName(mMapper.getName(tb.getM_id()));

        }

        rModel.setState(true);
        rModel.setMessage("게시글 목록을 불러왔습니다.");
        rModel.setResult(wList);

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

}
