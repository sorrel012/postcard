package com.postcard.toyou.model;

import lombok.Data;

@Data
public class ResultModel {

    private Boolean state;
    private Object result;
    private String message;
    private int totalRow;

}
