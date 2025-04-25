package com.example.emailservice.controller;

import com.example.emailservice.entity.Course;
import com.example.emailservice.repository.CourseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "Course", description = "API для управления курсами")
public class CourseController {
    private final CourseRepository courseRepository;

    @PostMapping
    @Operation(summary = "Создание нового курса")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseRepository.save(course));
    }

    @GetMapping
    @Operation(summary = "Получение списка всех курсов")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseRepository.findAll());
    }
} 