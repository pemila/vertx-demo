package com.pemila.vertx.constants;

/**
 * @author pemila
 * @date 2020/10/10 17:27
 **/
public interface HttpStatusCode {
    /** 请求成功*/
    Integer SUCCESS = 200;
    /** 客户端异常*/
    Integer REQUEST_ERROR = 400;
    /** 服务端异常*/
    Integer RESPONSE_ERROR = 500;
}
