package com.pemila.vertx;

import com.pemila.vertx.service.FailureHandler;
import com.pemila.vertx.service.LogHandler;
import com.pemila.vertx.service.RequestHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 启动类
 * @author pemila
 * @date 2020/9/29 12:36
 **/
public class MainVerticle extends AbstractVerticle {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private RequestHandler requestHandler;
    private FailureHandler failureHandler;

    @Override
    public void start(Promise<Void> promise){
        this.requestHandler = new RequestHandler();
        this.failureHandler = new FailureHandler();

        vertx.exceptionHandler(error -> log.error("未捕获的异常：",error));

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route("/*").handler(new LogHandler());
        router.route("/api/*").handler(requestHandler).failureHandler(failureHandler);
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
