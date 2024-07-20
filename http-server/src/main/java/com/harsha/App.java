package com.harsha;

import com.harsha.server.MyHttpServer;

public class App {
  
    public static void main( String[] args ) {
        new MyHttpServer().createServer(8080);
    }
}
