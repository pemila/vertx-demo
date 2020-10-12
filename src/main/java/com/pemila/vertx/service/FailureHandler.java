package com.pemila.vertx.service;

import com.pemila.vertx.constants.HttpStatusCode;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常记录
 * @author pemila
 * @date 2020/10/10 16:51
 **/
public class FailureHandler implements Handler<RoutingContext> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(RoutingContext event) {
        Throwable thrown = event.failure();
        recorderError(thrown);
        event.response().setStatusCode(HttpStatusCode.RESPONSE_ERROR).end();
    }

    private void recorderError(Throwable thrown) {
        // TODO record error
        log.error("error",thrown);
    }
}
