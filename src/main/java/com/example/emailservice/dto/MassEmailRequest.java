package com.example.emailservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Запрос на массовую рассылку email")
public class MassEmailRequest {
    @Schema(description = "Список email адресов для рассылки")
    private List<String> emails;

    @Schema(description = "Тема письма")
    private String subject;

    @Schema(description = "Текст письма")
    private String body;
} 