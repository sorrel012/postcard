package com.postcard.toyou.controller;

import com.postcard.toyou.common.PageCriteria;
import com.postcard.toyou.config.TagFilterUtil;
import com.postcard.toyou.dto.SearchDTO;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.model.TbCommentModel;
import com.postcard.toyou.model.TreasureBoxModel;
import com.postcard.toyou.service.TreasureBoxService;
import com.postcard.toyou.service.S3FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@Slf4j
@RestController
public class TreasureBoxController {

    private final TreasureBoxService tbService;
    private final S3FileUploadService s3Service;

    public TreasureBoxController(TreasureBoxService tbService, S3FileUploadService s3UploadService) {
        this.s3Service = s3UploadService;
        this.tbService = tbService;
    }

    @PostMapping("/treasure/image")
    public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: uploadImage ::: MultipartFile : {}",  file);
        }

        try {
            // 원본 파일명
            String originName = file.getOriginalFilename();

            // 저장될 새 파일명 생성
            String newFileName = java.util.UUID.randomUUID().toString() + "@" + originName;

            //aws s3에 저장 후 이미지 url 반환 받기
            String imageUrl = s3Service.saveFile(file, newFileName);

            return ResponseEntity.ok(Map.of(
                    "uploaded", 1,
                    "fileName", newFileName,
                    "originName", originName,
                    "url", imageUrl
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "uploaded", 0,
                    "error", Map.of("message", "파일을 업로드하지 못했습니다")
            ));
        }
    }

    @PostMapping("/post")
    public ResponseEntity<ResultModel> registPost(
            @RequestParam("content") String content,
            @RequestParam("title") String title,
            @RequestParam("id") String id,
            @RequestParam("images") String images
            ) {

        // 태그 필터링
        content = TagFilterUtil.filter(content);

        TreasureBoxModel tbModel = new TreasureBoxModel();
        tbModel.setTitle(title);
        tbModel.setContent(content);
        tbModel.setM_id(id);

        if ( log.isDebugEnabled() ) {
            log.debug("::: registPost ::: TreasureBoxModel : {}",  tbModel.toString());
        }

        JSONArray jsonArray = new JSONArray(images);
        List<String> imageList = new ArrayList<>();

        if(jsonArray.length() > 0) {
            for(int i = 0; i < jsonArray.length(); i++){
                String imageUrl = jsonArray.getString(i);
                imageList.add(imageUrl);
            }
        }

        return tbService.registPost(tbModel, imageList);
    }

    @PutMapping("/post")
    public ResponseEntity<ResultModel> editPost(
            @RequestParam("content") String content,
            @RequestParam("title") String title,
            @RequestParam("id") String id,
            @RequestParam("images") String images,
            @RequestParam("b_seq") int b_seq
            ) {

        // 태그 필터링
        content = TagFilterUtil.filter(content);

        TreasureBoxModel tbModel = new TreasureBoxModel();
        tbModel.setTitle(title);
        tbModel.setContent(content);
        tbModel.setM_id(id);
        tbModel.setB_seq(b_seq);

        if ( log.isDebugEnabled() ) {
            log.debug("::: editPost ::: TreasureBoxModel : {}",  tbModel.toString());
        }

        JSONArray jsonArray = new JSONArray(images);
        List<String> imageList = new ArrayList<>();

        if(jsonArray.length() > 0) {
            for(int i = 0; i < jsonArray.length(); i++){
                String imageUrl = jsonArray.getString(i);
                imageList.add(imageUrl);
            }
        }

        return tbService.editPost(tbModel, imageList);
    }

    @GetMapping("/postlist")
    public ResponseEntity<ResultModel> getPostList(
            @RequestParam int selectedOption,
            @RequestParam int searchOption,
            @RequestParam String searchKeyword,
            @RequestParam int pageNo,
            @RequestParam int size) {

        //검색 조건
        SearchDTO sDto = new SearchDTO();
        sDto.setSearchOption(searchOption);
        sDto.setSearchKeyword(searchKeyword);

        //정렬 조건
        sDto.setSelectedOption(selectedOption);

        // 페이징
        PageCriteria criteria = new PageCriteria();
        criteria.setPageNo(pageNo);
        criteria.setSize(size);

        if ( log.isDebugEnabled() ) {
            log.debug("::: searchPost ::: SearchDTO : {}",  sDto.toString());
            log.debug("::: searchPost ::: PageCriteria : {}",  criteria.toString());
        }

        return tbService.getPostList(sDto, criteria);
    }

    @PostMapping("/comment")
    public ResponseEntity<ResultModel> registComment(@RequestBody TbCommentModel tbcModel) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: registComment ::: TbCommentModel : {}",  tbcModel.toString());
        }

        return tbService.registComment(tbcModel);
    }

    @GetMapping("/commentlist")
    public ResponseEntity<ResultModel> getCommentList(@RequestParam int seq) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: getCommentList ::: int : {}",  seq);
        }

        return tbService.getCommentList(seq);
    }

    @PutMapping("/hit")
    public ResponseEntity<ResultModel> increaseHit(@RequestBody String seq) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: increaseHit ::: String : {}",  seq);
        }

        return tbService.increaseHit(Integer.valueOf(seq));
    }

    @DeleteMapping("/post")
    public ResponseEntity<ResultModel> deletePost(@RequestParam String seq) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: deletePost ::: String : {}",  seq);
        }

        return tbService.deletePost(Integer.valueOf(seq));
    }

    @GetMapping("mypostlist")
    public ResponseEntity<ResultModel> getMyPostList(
            @RequestParam String id,
            @RequestParam int pageNo,
            @RequestParam int size) {

        if ( log.isDebugEnabled() ) {
            log.debug("::: getMyPostList ::: String : {}",  id);
        }

        PageCriteria criteria = new PageCriteria();
        criteria.setPageNo(pageNo);
        criteria.setSize(size);

        return tbService.getMyPostList(id, criteria);
    }

}
