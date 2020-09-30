package com.tc.manager.service;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pemila
 * @date 2020/9/30 14:28
 **/
public class RequestHandler implements Handler<RoutingContext> {

    private Logger log = LogManager.getLogger(this.getClass());

    @Override
    public void handle(RoutingContext event) {

        log.info(event.normalisedPath());


        HttpServerRequest request = event.request();
        log.info(event.getBodyAsJson());
        log.info(Json.encode(request.query()));

        event.fail(404);
    }
}
