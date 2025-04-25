package com.example.emailservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(value = "student-service", url = "http://localhost:8081")
public interface StudentServiceClient {
    @GetMapping("/api/students/emails")
    List<String> getStudentEmailsByIds(@RequestParam("ids") List<Long> ids);
} 