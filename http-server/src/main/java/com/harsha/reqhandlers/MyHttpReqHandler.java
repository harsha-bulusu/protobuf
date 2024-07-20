package com.harsha.reqhandlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.harsha.common.Constants;
import com.harsha.util.Helper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHttpReqHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("test");
        Map<String, String> queryParams = Helper.extractQueryParams(exchange);
        int id = Integer.parseInt(queryParams.get("id"));
        int size = Integer.parseInt(queryParams.get("size"));
        
        //reading request header accept for content-negotiation
        Headers requestHeaders = exchange.getRequestHeaders();
        System.out.println(queryParams);
        List<String> accept = requestHeaders.get(Constants.ACCEPT_HEADER_VALUE);

        if ((accept.contains(Constants.APPLICATION_JSON_VALUE) && accept.contains(Constants.APPLICATION_PROTOBUF_VALUE))
            || accept.contains("*/*")) {
            //send json response - Default
            sendJsonResponse(exchange, id, size);
        } else if (accept.contains(Constants.APPLICATION_JSON_VALUE)) {
            //send json response
            sendJsonResponse(exchange, id, size);
        } else if (accept.contains(Constants.APPLICATION_PROTOBUF_VALUE)) {
            //send protobuf response
            sendProtoBufResponse(exchange, id, size);
        } else {
            //send error response Not Acceptable - 406
            String response = "The accepted media types are: [" + Constants.APPLICATION_JSON_VALUE + ", "
                + Constants.APPLICATION_PROTOBUF_VALUE + "]"; 
            exchange.sendResponseHeaders(406, response.getBytes().length);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(response.getBytes());
        }

        
    }

    private void sendJsonResponse(HttpExchange exchange, int id, int size) throws IOException {
        // Response Body
        String response = Helper.getJsonCollection(id, size);
        System.out.println(response);
        
        // Headers
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.add("Content-type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        
        // Response
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(response.getBytes());
        responseBody.close();
    }

    
    
    private void sendProtoBufResponse(HttpExchange exchange, int id, int size) throws IOException {
        // Response Body
        byte[] responseBytes = Helper.getProtoBufCollection(id, size);

        // Headers
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "application/x-protobuf");
        exchange.sendResponseHeaders(200, responseBytes.length);
        
        // Response
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(responseBytes);
        responseBody.close();
    }
}
