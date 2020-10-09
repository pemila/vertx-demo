package com.tc.manager;

import com.tc.manager.service.LogHandler;
import com.tc.manager.service.RequestHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 启动类
 * @author pemila
 * @date 2020/9/29 12:36
 **/
public class MainVerticle extends AbstractVerticle {

    private Logger log = LogManager.getLogger(this.getClass());

    @Override
    public void start(Promise<Void> promise){
        vertx.exceptionHandler(error -> log.error("未捕获的异常：",error));

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route("/*").handler(new LogHandler());
        router.route("/api/*").handler(new RequestHandler());
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(
                    config().getInteger("http:port",8080),
                    result ->{
                        if(result.succeeded()){
                            promise.complete();
                        }else{
                            promise.fail(result.cause());
                        }
                    });
    }
}
