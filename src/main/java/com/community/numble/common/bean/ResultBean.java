package com.community.numble.common.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultBean<T> {

    private boolean success;

    private int code;

    private String message;

    private T data;

}
