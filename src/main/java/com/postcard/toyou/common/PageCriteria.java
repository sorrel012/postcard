package com.postcard.toyou.common;

import lombok.Data;
import org.checkerframework.checker.index.qual.Positive;

@Data
public class PageCriteria {

    @Positive
    private int pageNo;     //현재 페이지 번호

    @Positive
    private int size;       //한 페이지에 표시될 개수
    
    private int totalRow;   //총 게시물 수

    public int getStartRow() {
        return ((this.pageNo - 1) * this.size) + 1;
    }

    public int getEndRow() {
        return ((this.pageNo - 1) * this.size) + this.size;
    }

}
