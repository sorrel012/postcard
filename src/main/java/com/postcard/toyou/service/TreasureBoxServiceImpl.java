package com.postcard.toyou.service;

import com.postcard.toyou.dao.TreasureBoxMapper;
import com.postcard.toyou.model.PostcardModel;
import com.postcard.toyou.model.ResultModel;
import com.postcard.toyou.model.TbPicModel;
import com.postcard.toyou.model.TreasureBoxModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreasureBoxServiceImpl implements TreasureBoxService {

    private final TreasureBoxMapper tbMapper;
    private final S3FileUploadService s3Service;

    public TreasureBoxServiceImpl(TreasureBoxMapper tbMapper, S3FileUploadService s3UploadService) {
        this.tbMapper = tbMapper;
        this.s3Service = s3UploadService;
    }

    @Override
    public ResponseEntity<ResultModel> registWriting(TreasureBoxModel writing, List<String> imageList) {

        ResultModel rModel = new ResultModel();
        
        //게시글 DB 저장
        int writingResult = tbMapper.registWriting(writing);
        
        //본문 내용 저장 성공 && 이미지를 업로드 한 적 있는지 확인
        if(writingResult>0 && !imageList.isEmpty()){

            //게시글 이미지 추출
            List<String> contentImages = new ArrayList<>();
            Document doc = Jsoup.parse(writing.getContent());
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
            int picResult = 0;
            for(String url : contentImages) {
                url = s3Service.decodeUrl(url);
                String imgName = s3Service.extractFileName(url).split("@")[1];

                TbPicModel tbpModel = new TbPicModel();
                tbpModel.setB_seq(writingResult);
                tbpModel.setPic_url(url);
                tbpModel.setPic_name(imgName);

                picResult = tbMapper.registPic(tbpModel);
            }

        }

        // 본문 저장


//
//        rModel.setState(true);
//        rModel.setMessage("쪽지 목록을 불러왔습니다.");
//        rModel.setResult(pcList);

        return ResponseEntity.ok(rModel);
    }

}
