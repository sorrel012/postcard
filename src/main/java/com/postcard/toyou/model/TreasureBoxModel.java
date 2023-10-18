package com.postcard.toyou.model;

import lombok.Data;

@Data
public class TreasureBoxModel {
    
    private String b_seq;   //게시글 번호
    private String m_id;    //작성자 아이디
    private String title;   //제목
    private String content; //내용
    private String regdate; //작성시간
    private int hit;        //조회수

    private String name;    //작성자 이름;
    
}
