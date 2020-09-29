package com.tc.manager;

import com.tc.manager.verticle.HelloWorldVerticle;
import io.vertx.core.AbstractVerticle;

/**
 * 启动类
 * @author pemila
 * @date 2020/9/29 12:36
 **/
public class MainVerticle extends AbstractVerticle {
    @Override
    public void start(){
        // 部署对应的verticle
        vertx.deployVerticle(HelloWorldVerticle.class.getName());
    }
}
