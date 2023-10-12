package com.postcard.toyou.model;

import lombok.Data;

@Data
public class TbPicModel {

    private String pic_url;     //사진 URL
    private String pic_name;    //사진 파일명
    private int b_seq;          // 게시글 번호
    
}
