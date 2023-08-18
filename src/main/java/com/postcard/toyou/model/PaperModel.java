package com.postcard.toyou.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaperModel {

    private int pcc_seq;            //도화지 번호
    private int m_seq;              //회원 번호
    private String title;           //도화지 제목
    private String bgColor;         //도화지 배경 색상
    private String pcColor;         //쪽지 배경 색상
    private String pcBorderPx;      //쪽지 테두리 두께
    private String pcbColor;        //쪽지 테두리 색상
    private String pcbRadiusPx;     //쪽지 테두리 둥글기
    private int code;               //도화지 코드(공유용)
    private String regdate;         //생성 날짜

    private int pcCnt;              //답변 개수

}
