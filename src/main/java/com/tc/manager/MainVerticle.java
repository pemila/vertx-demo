package com.tc.manager;

import com.tc.manager.api.ApiVerticle;
import com.tc.manager.service.LogService;
import com.tc.manager.service.RequestHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
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
//        vertx.deployVerticle(new ApiVerticle(),new DeploymentOptions().setConfig(config()));
//        vertx.deployVerticle(new LogService());


        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route("/api/*").handler(new RequestHandler());
//        router.route().last().handler(c -> c.fail(404)).failureHandler(this::returnError);
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
