package com.harsha.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.harsha.reqhandlers.MyHttpReqHandler;
import com.sun.net.httpserver.HttpServer;


public class MyHttpServer {

    private static final Logger log = LoggerFactory.getLogger(MyHttpServer.class);

    public void createServer(int port) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            httpServer.createContext("/", new MyHttpReqHandler());
            httpServer.start();
            log.info("server started running on port: {}", port);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
