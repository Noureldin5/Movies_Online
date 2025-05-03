package org.example.midterm.config;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject("Email Verification");

        String verificationUrl = "http://localhost:8080/register/verify?token=" + token;
        String emailContent =
            "<div>" +
            "<h1>Email Verification</h1>" +
            "<p>Please click the link below to verify your email address:</p>" +
            "<a href='" + verificationUrl + "'>Verify Email</a>" +
            "</div>";

        helper.setText(emailContent, true);
        mailSender.send(message);
    }
}