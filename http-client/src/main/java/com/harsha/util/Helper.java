package com.harsha.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.harsha.entity.Employees;
import com.harsha.proto.EmployeeProto.EmployeeList;

public class Helper {

    private static final Logger log = LoggerFactory.getLogger(Helper.class);


    public static void invokeForProtBuf(MyHttpClient httpClient, int id, int size) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/x-protobuf");
        byte[] responseBody = httpClient.invokeRequest("http://localhost:8080?id=" + id + "&size=" + size, headers, size);
        try {
            long startTime = System.currentTimeMillis();
            EmployeeList.parseFrom(responseBody);
            long endTime = System.currentTimeMillis();
            log.info("Time taken for Deserialization from ProtoBuf to {} Java objects is: {}ms", size, (endTime - startTime));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public static void invokeForJson(MyHttpClient httpClient, int id, int size) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        byte[] responseBody = httpClient.invokeRequest("http://localhost:8080?id=" + id + "&size=" + size, headers, size);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            long startTime = System.currentTimeMillis();
            objectMapper.readValue(responseBody, Employees.class);
            long endTime = System.currentTimeMillis();
            log.info("Time taken for Deserialization from JSON to {} Java objects is: {}ms", size, (endTime - startTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
