package com.xiaotree.jinyuserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    public static <T> Result<T> success(T data) {
        return success("访问成功", data);
    }

    public static Result<Boolean> success(){
        return success(true);
    }

    public static <T> Result<T> error(String msg, T data) {
        return new Result<>(400, msg, data);
    }

    public static <T> Result<T> error(T data) {
        return error("访问失败", data);
    }

    public static Result<Boolean> error(){
        return error(false);
    }
}
