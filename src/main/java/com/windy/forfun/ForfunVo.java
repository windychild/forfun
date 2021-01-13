package com.windy.forfun;

public class ForfunVo {

    private Integer pre;
    private Integer next;
    private String keyWord;
    private String[] arr;
    private Integer totalCount;

    public Integer getTotalCount() {
        return totalCount;
    }


    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPre() {
        return pre;
    }

    public void setPre(Integer pre) {
        this.pre = pre;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }

    public Integer getNext() {
        return next;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public String[] getArr() {
        return arr;
    }


}
