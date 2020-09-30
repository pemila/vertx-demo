package com.tc.manager.service;

import com.tc.manager.constants.Key;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pemila
 * @date 2020/9/29 17:00
 **/
public class LogService extends AbstractVerticle {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.eventBus().consumer(Key.LOG_RECORD,this::saveLog);
        startPromise.complete();

    }

    private <T> void saveLog(Message<T> message) {
        System.out.println(message.body());
        message.reply("aaaa");
    }
}
