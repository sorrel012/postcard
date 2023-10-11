package com.postcard.toyou.controller;

import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.model.TreasureBoxModel;
import com.postcard.toyou.service.TreasureBoxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class TreasureBoxController {

    private final TreasureBoxService tbService;

    public TreasureBoxController(TreasureBoxService tbService) {
        this.tbService = tbService;
    }

    @PostMapping("/treasure-image")
    public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) {
        try {
            System.out.println(file);

            String imageUrl = "...";  // 이미지의 URL

            return ResponseEntity.ok(Map.of(
                    "uploaded", 1,
                    "fileName", file.getOriginalFilename(),
                    "url", imageUrl
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "uploaded", 0,
                    "error", Map.of("message", "could not upload the file")
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

        System.out.println(tbModel);

        return tbService.registWriting(tbModel);
    }


}
