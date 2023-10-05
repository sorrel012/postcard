package com.postcard.toyou.controller;

import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.service.PostcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class TreasureBoxController {

    @Autowired
    private TreasureBoxService tbService;

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
    public ResponseEntity<ResultModel> registWriting(@RequestBody TbWritingModel writing) {
        return tbService.registWriting(writing);
    }


}
