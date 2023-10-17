package com.postcard.toyou.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberModel {

    private String id;              //아이디
    private String pw;              //비밀번호
    private String name;            //이름
    private String tel;             //연락처
    private String email;           //이메일
    private String address;         //주소
    private String addressDetail;   //상세주소
    private String regDate;         //가입일자
    private String birth;           //생일

}
