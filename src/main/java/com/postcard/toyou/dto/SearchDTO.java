package com.postcard.toyou.dto;

import lombok.Data;

@Data
public class SearchDTO {
    
    private int searchOption;       //검색 조건
    private String searchKeyword;   //검색어
    
}
