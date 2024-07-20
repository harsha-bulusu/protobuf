package com.harsha.app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.harsha.app.entity.JsonProto;
import com.harsha.app.entity.Example.ExampleMessage;

import java.util.Arrays;

import org.springframework.http.HttpHeaders;
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
        byte[] responseBytes = message.toByteArray();
        HttpHeaders httpHeaders = new HttpHeaders();
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