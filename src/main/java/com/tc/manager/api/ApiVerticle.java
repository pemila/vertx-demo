package com.tc.manager.api;

import com.tc.manager.constants.Key;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author pemila
 * @date 2020/9/29 16:30
 **/
public class ApiVerticle extends AbstractVerticle {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void start(Promise<Void> promise) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/*").handler(this::log);
        router.get("/api/*").handler(this::api);
        router.route().last().handler(c -> c.fail(404)).failureHandler(this::returnError);
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


    private void api(RoutingContext routingContext){
        log.info("info:{}",routingContext);
        routingContext.request().response().write("hello world").end();

        routingContext.fail(404);

    }

    private Promise<JsonObject> parseParam(RoutingContext routingContext) {
        Promise<JsonObject> promise = Promise.promise();
        MultiMap map = routingContext.request().params();
        return promise;
    }


    /** 异常处理*/
    private void returnError(RoutingContext routingContext) {
    }

    /** 日志记录*/
    private void log(RoutingContext routingContext) {
        vertx.eventBus().send(Key.LOG_RECORD,routingContext);
        routingContext.next();
    }
}
