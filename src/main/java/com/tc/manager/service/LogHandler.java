package com.tc.manager.service;

import com.tc.manager.constants.HttpStatusCode;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.net.SocketAddress;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.LoggerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pemila
 * @date 2020/9/29 17:00
 **/
public class LogHandler implements LoggerHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final boolean immediate;

    public LogHandler(boolean immediate) {
        this.immediate = immediate;
    }
    public LogHandler() {
        this(false);
    }


    @Override
    public void handle(RoutingContext context) {
        // common logging data
        long timestamp = System.currentTimeMillis();
        String remoteClient = getClientAddress(context.request().remoteAddress());
        HttpMethod method = context.request().method();
        String uri = context.request().uri();
        HttpVersion version = context.request().version();

        if (immediate) {
            log(context, timestamp, remoteClient, version, method, uri);
        } else {
            context.addBodyEndHandler(v -> log(context, timestamp, remoteClient, version, method, uri));
        }

        context.next();
    }

    private String getClientAddress(SocketAddress inetSocketAddress) {
        if (inetSocketAddress == null) {
            return null;
        }
        return inetSocketAddress.host();
    }


    private void log(RoutingContext context, long timestamp, String remoteClient, HttpVersion version, HttpMethod method, String uri) {
        HttpServerRequest request = context.request();
        long contentLength = 0;
        if (immediate) {
            Object obj = request.headers().get("content-length");
            if (obj != null) {
                try {
                    contentLength = Long.parseLong(obj.toString());
                } catch (NumberFormatException e) {
                    contentLength = -1;
                }
            }
        } else {
            contentLength  = request.response().bytesWritten();
        }
        int status = request.response().getStatusCode();
        long time = System.currentTimeMillis() - timestamp;
        String message = String.format("%s %s %d %d %dms %s",method,uri,status,contentLength,time,context.getBodyAsString());
        doLog(status, message);
    }

    protected void doLog(int status, String message) {
        if (status >= HttpStatusCode.RESPONSE_ERROR) {
            log.error(message);
        } else if (status >= HttpStatusCode.REQUEST_ERROR) {
            log.warn(message);
        } else {
            log.info(message);
        }
    }
}