package com.pemila.vertx.verticle;

import io.vertx.core.AbstractVerticle;

/**
 * @author pemila
 * @date 2020/9/29 12:43
 **/
public class HelloWorldVerticle extends AbstractVerticle {

    @Override
    public void start(){
        vertx.createHttpServer().requestHandler(req->{
            req.response().putHeader("content-type","text/plain").end("hello world!");
        }).listen(8080);
    }
}
