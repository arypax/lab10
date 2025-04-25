package com.example.emailservice.controller;

import com.example.emailservice.dto.EmailRequest;
import com.example.emailservice.dto.MassEmailRequest;
import com.example.emailservice.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
@Tag(name = "Email Controller", description = "API для отправки email-сообщений")
public class EmailController {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @PostMapping("/simple")
    @Operation(summary = "Отправка простого email")
    public ResponseEntity<String> sendSimpleEmail(@RequestBody EmailRequest request) {
        emailService.sendSimpleEmail(request);
        return ResponseEntity.ok("Email sent successfully");
    }

    @PostMapping("/html")
    @Operation(summary = "Отправка HTML email")
    public ResponseEntity<String> sendHtmlEmail(@RequestBody EmailRequest request) {
        try {
            emailService.sendHtmlEmail(request);
            return ResponseEntity.ok("HTML email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send HTML email: " + e.getMessage());
        }
    }

    @PostMapping("/attachment")
    @Operation(summary = "Отправка email с вложением")
    public ResponseEntity<String> sendEmailWithAttachment(
            @RequestPart("request") EmailRequest request,
            @RequestPart("file") MultipartFile file) {
        try {
            emailService.sendEmailWithAttachment(request, file);
            return ResponseEntity.ok("Email with attachment sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send email with attachment: " + e.getMessage());
        }
    }

    @PostMapping("/mass")
    @Operation(summary = "Массовая рассылка email по ID студентов")
    public ResponseEntity<String> sendMassEmail(@RequestBody MassEmailRequest request) {
        try {
            emailService.sendMassEmail(request);
            return ResponseEntity.ok("Mass emails sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send mass emails: " + e.getMessage());
        }
    }
} 