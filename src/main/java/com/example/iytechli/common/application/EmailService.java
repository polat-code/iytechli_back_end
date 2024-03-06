package com.example.iytechli.common.application;


import com.example.iytechli.common.exception.EmailCannotSentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EmailService implements Notification {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String subject,String text,String to) throws Exception {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(text);
            simpleMailMessage.setTo(to);

            javaMailSender.send(simpleMailMessage);
        }catch (Exception e) {

            throw new EmailCannotSentException("There is a problem in email");
        }


    }
}
