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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3FileUploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * aws에 파일을 저장하는 메소드
     * 
     * @param multipartFile
     * @param newFileName
     * @return
     * @throws IOException
     */
    public String saveFile(MultipartFile multipartFile, String newFileName) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, newFileName, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, newFileName).toString();
    }

    /**
     * aws에서 파일을 삭제하는 메소드
     * 
     * @param deletedImg
     */
    public void deleteImage(List<String> deletedImg)  {

        for (String img : deletedImg) {

            String decodedUrl = decodeUrl(img);
            img = extractFileName(decodedUrl);

            amazonS3.deleteObject(bucket, img);
        }

    }

    /**
     * aws 파일 url을 디코딩하는 메소드
     *
     * @param imgUrl : aws 파일 url
     * @return
     */
    public String decodeUrl(String imgUrl) {

        //URL 디코딩
        String decodedUrl = "";
        try {
            decodedUrl = URLDecoder.decode(imgUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decodedUrl;
    }

    /**
     * url에서 파일명을 추출하는 메소드
     * 
     * @param decodedUrl : 디코딩된 파일 url
     * @return
     */
    public String extractFileName(String decodedUrl) {
        String awsUrl = "https://postcard17.s3.ap-northeast-2.amazonaws.com/";
        String fileName = decodedUrl.substring(awsUrl.length());

        return fileName;
    }

}