package com.tc.manager.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

/**
 * Http服务
 * @author pemila
 * @date 2020/9/29 14:09
 **/
public class HttpServerVerticle extends AbstractVerticle {

    @Override
    public void start(){
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);



        Route route = router.route().path("/*");
        route.handler(routingContext -> {

            HttpServerResponse response = routingContext.response();
            response.setChunked(true);

            response.putHeader("content-type","text/plain");
            response.end("Hello World from Vert.x Web");

        });
        server.requestHandler(router).listen(8080);
    }
}
