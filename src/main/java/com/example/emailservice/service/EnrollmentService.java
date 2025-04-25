package com.example.emailservice.service;

import com.example.emailservice.entity.Course;
import com.example.emailservice.entity.Enrollment;
import com.example.emailservice.entity.Student;
import com.example.emailservice.repository.CourseRepository;
import com.example.emailservice.repository.EnrollmentRepository;
import com.example.emailservice.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EmailService emailService;

    @Transactional
    public Enrollment createEnrollment(Long studentId, Long courseId) {
        // Проверяем, не записан ли уже студент на этот курс
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new RuntimeException("Student is already enrolled in this course");
        }

        // Получаем студента и курс по ID
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        // Создаем новое зачисление
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setActive(true);

        // Сохраняем в базу
        enrollment = enrollmentRepository.save(enrollment);

        try {
            // Отправляем уведомление на email
            emailService.sendEnrollmentConfirmation(enrollment);
        } catch (Exception e) {
            // Логируем ошибку, но не прерываем транзакцию
            log.error("Failed to send enrollment confirmation email to {}: {}", 
                student.getEmail(), e.getMessage());
        }

        return enrollment;
    }
} 