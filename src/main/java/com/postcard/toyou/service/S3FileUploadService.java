package com.postcard.toyou.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3FileUploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile, String newFileName) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, newFileName, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, newFileName).toString();
    }

    public void deleteImage(List<String> deletedImg)  {

        for (String img : deletedImg) {

            String decodedUrl = "";
            
            //URL 디코딩
            try {
                decodedUrl = URLDecoder.decode(img, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String awsUrl = "https://postcard17.s3.ap-northeast-2.amazonaws.com/";
            img = decodedUrl.substring(awsUrl.length());

            amazonS3.deleteObject(bucket, img);
        }

    }

}