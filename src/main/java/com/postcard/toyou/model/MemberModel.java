package com.postcard.toyou.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberModel {

    private int m_seq;
    private String id;
    private String pw;
    private String name;
    private String tel;
    private String email;
    private String address;
    private String addressDetail;
    private LocalDateTime regDate;
    private String birth;

}
