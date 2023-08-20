package com.postcard.toyou.model;

import lombok.Data;

@Data
public class PostcardModel {

    private int pc_seq;         //쪽지 번호
    private int pcc_seq;        //도화지 번호
    private String content;     //쪽지 내용
    private String regDate;     //쪽지 작성 시간
    private String textColor;   //글씨 색상

}
