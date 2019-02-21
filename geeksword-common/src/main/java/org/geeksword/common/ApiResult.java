package org.geeksword.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

/**
 * @Author zhoulinshun
 * @Description: rpc通用返回包装类
 * @Date: Created in 2018/5/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> implements Serializable {
    private String msg;

    private int code;

    private T data;


    public ApiResult(Response response) {
        msg = response.getMsg();
        code = response.getCode();
    }

    public ApiResult(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public ApiResult(Response response, T data) {
        this(response);
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return code == Response.SUCCESS.getCode();
    }

    public static <T> ApiResult<T> build() {
        return new ApiResult<>(Response.SUCCESS);
    }

    public static <T> ApiResult<T> build(String msg) {
        return build(msg, Response.SUCCESS.getCode());
    }

    public static <T> ApiResult<T> build(int code) {
        return build(Response.SUCCESS.getMsg(), code);
    }

    public static <T> ApiResult<T> build(T object) {
        return new ApiResult<>(Response.SUCCESS, object);
    }

    public static <T> ApiResult<T> build(String msg, int code) {
        return new ApiResult<>(msg, code);
    }

    public static <T> ApiResult<T> buildFail() {
        return new ApiResult<>(Response.FAIL);
    }

    public static <T> ApiResult<T> buildFail(String msg) {
        return buildFail(msg, Response.FAIL.getCode());
    }

    public static <T> ApiResult<T> buildFail(int code) {
        return buildFail(Response.FAIL.getMsg(), code);
    }

    public static <T> ApiResult buildFail(T object) {
        return new ApiResult<>(Response.FAIL, object);
    }

    public static <T> ApiResult<T> buildFail(String msg, int code) {
        return new ApiResult<>(msg, code);
    }


    public static enum Response {
        SUCCESS("success", 100),
        FAIL("fail", 101);

        private final String msg;
        private final int code;

        Response(String msg, int code) {
            this.msg = msg;
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public int getCode() {
            return code;
        }
    }
}
