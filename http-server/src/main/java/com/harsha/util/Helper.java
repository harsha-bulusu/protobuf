package com.harsha.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.harsha.entity.Employee;
import com.harsha.entity.Employees;
import com.harsha.proto.EmployeeProto.EmployeeList;
import com.harsha.proto.EmployeeProto.EmployeeList.Builder;
import com.sun.net.httpserver.HttpExchange;

public class Helper {

    private static final Logger log = LoggerFactory.getLogger(Helper.class);

    public static Map<String, String> extractQueryParams(HttpExchange exchange) {
        System.out.println("req query");
        URI requestURI = exchange.getRequestURI();
        String rawQuery = requestURI.getRawQuery();
        Map<String, String> queryParams = new HashMap<>();
        String[] params = rawQuery.split("&");
        for (String param : params) {
            String[] keyAndValue = param.split("=");
            queryParams.put(keyAndValue[0], keyAndValue[1]);
        }
        System.out.println(queryParams);
        return queryParams;
    }

    public static byte[] getProtoBufCollection(int id, int size) {
        Builder employeeListBuilder = EmployeeList.newBuilder();

        for (int i = 1; i <= size; i++) {
            com.harsha.proto.EmployeeProto.Employee employee = com.harsha.proto.EmployeeProto.Employee.newBuilder()
            .setAge(i).setName("Test-" + i + "-" + id).setId(i + id).build();
            employeeListBuilder = employeeListBuilder.addEmployees(employee);
        }

        EmployeeList employeeList = employeeListBuilder.build();
        long startTime = System.currentTimeMillis();
        byte[] serializedEmployeeList = employeeList.toByteArray();
        long endTime = System.currentTimeMillis();
        log.info("Time taken for serializing {} Java Objects into protobuf is: {}ms", size, (endTime - startTime) );
        return serializedEmployeeList;
    }

    public static String getJsonCollection(int id, int size) {
        System.out.println("getJsonCollection");
        Employees employees = new Employees();
        List<Employee> employeeCollection = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            employeeCollection.add(new Employee(i + id, "Test-" + i + "-" + id, i));
        }

        employees.setEmployees(employeeCollection);
        JsonSerializer<Employees> jsonSerializer = new JsonSerializer<>();
        long startTime = System.currentTimeMillis();
        String json = jsonSerializer.serialize(employees);
        long endTime = System.currentTimeMillis();
        log.info("Time taken for serializing {} Java Objects into JSON is: {}ms", size, (endTime - startTime) );
        return json;
    }
    
}
