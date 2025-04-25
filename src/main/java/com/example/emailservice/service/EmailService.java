package com.example.emailservice.service;

import com.example.emailservice.dto.EmailRequest;
import com.example.emailservice.dto.MassEmailRequest;
import com.example.emailservice.entity.Enrollment;
import com.example.emailservice.entity.Student;
import com.example.emailservice.repository.StudentRepository;
import com.example.emailservice.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    public void sendEnrollmentConfirmation(Enrollment enrollment) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(enrollment.getStudent().getEmail());
            message.setSubject("Enrollment Confirmation");
            
            String emailBody = String.format(
                "Dear %s,\n\n" +
                "You have been successfully enrolled in the course \"%s\" starting from %s.\n\n" +
                "Best regards,\n" +
                "Course Management System",
                enrollment.getStudent().getName(),
                enrollment.getCourse().getTitle(),
                enrollment.getEnrollmentDate().format(DATE_FORMATTER)
            );
            
            message.setText(emailBody);
            mailSender.send(message);
            log.info("Enrollment confirmation email sent to: {}", enrollment.getStudent().getEmail());
        } catch (Exception e) {
            log.error("Failed to send enrollment confirmation email: {}", e.getMessage());
            // Не выбрасываем исключение, чтобы не откатить транзакцию записи на курс
        }
    }

    @Transactional
    public void sendMassEmail(MassEmailRequest request) {
        for (String email : request.getEmails()) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject(request.getSubject());
                message.setText(request.getBody());
                mailSender.send(message);
                
                log.info("Email sent successfully to: {}", email);
            } catch (Exception e) {
                log.error("Failed to send email to {}: {}", email, e.getMessage());
            }
        }
    }

    @Transactional
    public void sendSimpleEmail(EmailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getTo());
        message.setSubject(request.getSubject());
        message.setText(request.getBody());
        mailSender.send(message);
    }

    @Transactional
    public void sendHtmlEmail(EmailRequest request) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(request.getTo());
        helper.setSubject(request.getSubject());
        helper.setText(request.getBody(), true);
        mailSender.send(message);
    }

    @Transactional
    public void sendEmailWithAttachment(EmailRequest request, MultipartFile file) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(request.getTo());
        helper.setSubject(request.getSubject());
        helper.setText(request.getBody());
        helper.addAttachment(file.getOriginalFilename(), file);
        mailSender.send(message);
    }
} 