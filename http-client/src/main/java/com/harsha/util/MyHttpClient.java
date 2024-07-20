package com.harsha.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class MyHttpClient {

    private static final Logger log = LoggerFactory.getLogger(MyHttpClient.class);

    private HttpClient httpClient;

    public MyHttpClient() {
        httpClient = HttpClient.newHttpClient();
    }

    private HttpRequest buildRequest(String url, Map<String, String> headers) {
        try {
            Builder httpReqBuilder = HttpRequest.newBuilder(new URI(url))
                .GET();
            addHeaders(httpReqBuilder, headers);
            return 
                httpReqBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addHeaders(Builder httpReqBuilder, Map<String, String> headers) {
        for (Entry<String, String> header : headers.entrySet()) {
            httpReqBuilder.header(header.getKey(), header.getValue());
        }
    }

    public byte[] invokeRequest(String url, Map<String, String> headers, int size) {
        HttpRequest request = buildRequest(url, headers);
        try {
            long startTime = System.currentTimeMillis();
            HttpResponse<byte[]> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            long endTime = System.currentTimeMillis();
            String reqType = headers.get("Accept").split("/")[1].equals("json") ? "JSON" : "ProtoBuf";
            log.info("Response time for {} req with {} Java objects is: {}ms", reqType, size, (endTime - startTime));
            log.info("Size of {} serialized data with {} Java objects is: {}bytes", reqType, size, httpResponse.body().length);
            return httpResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void printHeaders(HttpResponse httpResponse) {
        HttpHeaders responseHeaders = httpResponse.headers();
        Set<Entry<String, List<String>>> entrySet = responseHeaders.map().entrySet();
        for (Entry<String, List<String>> entry : entrySet) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
    
}
