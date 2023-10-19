package com.postcard.toyou.dto;

import lombok.Data;

@Data
public class SearchDTO {
    
    private int searchOption;       //검색 조건
    private String searchKeyword;   //검색어
    private int selectedOption;     //정렬 조건
    
}
