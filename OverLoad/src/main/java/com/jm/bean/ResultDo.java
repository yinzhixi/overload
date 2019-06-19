package com.jm.bean;

/**
 * @Description ResultDo.java
 * @author equat
 * @param <T>
 * @date 2018/03/22 23:37:80
 */
public class ResultDo<T> {
    private int code;
    private String msg;
    private int count;
    private T data;

    public ResultDo() {
        super();
    }

    public ResultDo(T data) {
        super();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(long l) {
        this.count = new Long(l).intValue();
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

}
