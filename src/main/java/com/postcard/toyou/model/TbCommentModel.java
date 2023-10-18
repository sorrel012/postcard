package com.postcard.toyou.model;

import lombok.Data;

@Data
public class TbCommentModel {

    private int c_seq;          //댓글 번호
    private String content;     //댓글 내용
    private int b_seq;          //게시글 번호
    private String regdate;     //작성일시
    private String m_id;        //작성자 아이디
    
    private String name;        //작성자 이름
    private String writer;      //작성자 출력용

}
