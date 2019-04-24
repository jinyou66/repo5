package com.itheima.demo.exception.enums;

public enum ExceptionTypeCode {

    CODE1("1001"),
    CODE2("1002");
    private String code;

    private ExceptionTypeCode(String code){
        this.code = code;
    }
}
