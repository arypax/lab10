package com.example.emailservice.controller;

import com.example.emailservice.entity.Enrollment;
import com.example.emailservice.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollment", description = "API для управления зачислениями")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    @Operation(summary = "Создание нового зачисления")
    public ResponseEntity<?> createEnrollment(
            @RequestParam Long studentId,
            @RequestParam Long courseId) {
        try {
            Enrollment enrollment = enrollmentService.createEnrollment(studentId, courseId);
            return ResponseEntity.ok(enrollment);
        } catch (Exception e) {
            log.error("Error creating enrollment: {}", e.getMessage());
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Failed to create enrollment: " + e.getMessage()));
        }
    }
}

class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
} 