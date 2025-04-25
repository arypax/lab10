package com.example.emailservice.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class EmailResponse {
    private boolean success;
    private String message;
    private String error;
} 