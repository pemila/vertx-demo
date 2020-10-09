package com.tc.manager.service;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import io.vertx.ext.web.RoutingContext;

/**
 * @author pemila
 * @date 2020/9/30 14:28
 **/
public class RequestHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext event) {
        Buffer buffer = new BufferImpl();
        buffer.appendString("this is a world !");
        event.response().end(buffer);
    }
}
