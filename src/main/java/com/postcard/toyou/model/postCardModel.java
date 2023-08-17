package com.postcard.toyou.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class postCardModel {

    private int pc_seq;
    private String content;
    private LocalDateTime regDate;
    private int pcc_seq;

}
