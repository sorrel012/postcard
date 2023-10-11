package com.postcard.toyou.controller;

import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.model.TreasureBoxModel;
import com.postcard.toyou.service.TreasureBoxService;
import com.postcard.toyou.service.S3FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
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
        try {

            // 원본 파일명
            String originName = file.getOriginalFilename();

            // 확장자
            String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

            // 저장될 새 파일명 생성
            String newFileName = java.util.UUID.randomUUID().toString() + "." + fileExtension;

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

    @PostMapping("/writing")
    public ResponseEntity<ResultModel> registWriting(
            @RequestParam("content") String content,
            @RequestParam("title") String title,
            @RequestParam("id") String id
            ) {

        TreasureBoxModel tbModel = new TreasureBoxModel();
        tbModel.setTitle(title);
        tbModel.setContent(content);
        tbModel.setM_id(id);

        return tbService.registWriting(tbModel);
    }


}
