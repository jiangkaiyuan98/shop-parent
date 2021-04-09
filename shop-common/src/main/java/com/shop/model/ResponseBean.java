package com.shop.model;

/**
 * @program: community
 * @description: 返回的json数据通用格式
 * @author: jiangkaiyuan
 * @create: 2020-01-02 14:58
 */
public class ResponseBean {

//    public static int SUCCESS = 200;
//    public static int FAILURE = 0;
//    public static int ERROR = -1;
//    public static int NOT_LEGAL = -100;

    // http 状态码
    private int code;

    // 返回信息
    private String msg;

    // 数据个数
    private int count;

    // 返回的数据
    private Object data;

    public ResponseBean(int code, String msg, int count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}