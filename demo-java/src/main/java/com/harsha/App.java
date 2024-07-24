package com.harsha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;

class Employee {
    private int id;
    private String name;
    private String role;

    public Employee() {}
    
    public Employee(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}

/**
 * Hello world!
 *
 */
public class App 
{

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void printBytesInHex(byte[] bytes) {
        for (byte b : bytes) {
            System.out.printf("%02X ", b);
        }
        System.out.println();
    }

    public static void main( String[] args )
    {
        /**Json Serialization */
        Employee employee = new Employee(1, "Harsha", "Engineer");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(employee);
            log.info("Serialized json: {}", json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        /**Protobuf serialization */
        com.harsha.EmployeeOuterClass.Employee employee2 = com.harsha.EmployeeOuterClass.Employee.newBuilder()
        .setId(1).setName("Harsha").setRole("Engineer").build();
        byte[] serializedProtoBuf = employee2.toByteArray();
        printBytesInHex(serializedProtoBuf);
        
        /**
         * JSON Deserialization
         */
        try {
            Employee employeeFromJson = objectMapper.readValue(json.getBytes(), Employee.class);
            log.info("Name: {}, Role: {}, id: {}", employeeFromJson.getName(), employeeFromJson.getRole()
            ,employeeFromJson.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * Protobuf deserialization
         */
        try {
            com.harsha.EmployeeOuterClass.Employee employeeFromProtoBuf = com.harsha.EmployeeOuterClass.Employee.parseFrom(serializedProtoBuf);
            log.info("Name: {}, Role: {}, id: {}", employeeFromProtoBuf.getName(), employeeFromProtoBuf.getRole(),
            employeeFromProtoBuf.getId());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
