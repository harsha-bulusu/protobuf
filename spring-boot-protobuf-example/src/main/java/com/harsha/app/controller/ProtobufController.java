package com.harsha.app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.harsha.app.entity.JsonProto;
import com.harsha.app.entity.Example.ExampleMessage;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ProtobufController {



    @GetMapping(value = "/", produces = "application/x-protobuf")
    public ResponseEntity<byte[]> getSampleProtobufResponse(@RequestParam int id, @RequestParam String username) {
        ExampleMessage message = ExampleMessage.newBuilder()
        .setId(id)
        .setName(username).build();
        System.out.println("Request received for protobuf " + id + " " + username);
        byte[] responseBytes = message.toByteArray();
        HttpHeaders httpHeaders = new HttpHeaders();
        System.out.println("Serialized message length: " + responseBytes.length);
        System.out.println("Serialized message bytes: " + Arrays.toString(responseBytes));
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROTOBUF_VALUE);
        return ResponseEntity.ok()
            .headers(httpHeaders)
            .body(responseBytes);
    }

    @GetMapping("/json")
    public JsonProto getMethodName(@RequestParam int id, @RequestParam String username) {
        return new JsonProto(id, username);
    }

    
    
    

}