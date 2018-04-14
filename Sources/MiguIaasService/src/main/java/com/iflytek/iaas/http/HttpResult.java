package com.iflytek.iaas.http;

import java.util.Map;

public class HttpResult {

    public static final int OK = 200;

    // 响应码
    private Integer code;

    Map<String,String> headers;

    // 响应体
    private String body;

    public HttpResult() {
        super();
    }

    public HttpResult(Integer code, String body) {
        super();
        this.code = code;
        this.body = body;
    }

    public HttpResult(Integer code, Map<String, String> headers, String body) {
        this.code = code;
        this.headers = headers;
        this.body = body;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
