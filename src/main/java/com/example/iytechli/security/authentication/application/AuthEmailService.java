package com.example.iytechli.security.authentication.application;

import com.example.iytechli.common.application.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthEmailService {

    private final EmailService emailService;
    public void sendAuthEmail(String otp, String name,String surname,String to) throws Exception {
        emailService.sendEmail("Iytechli Email Onaylama",
                "Merhaba, " + name + " " + surname + "\nEmailini onaylamak için bu kodu kullanabilirsin: "
                        + otp +"\n \nUnutma bu kod sadece 24 saat geçerlidir." , to );

    }
    public void sendRefreshPasswordCodeEmail(String refreshPasswordCode, String name,String surname,String to) throws Exception {
        emailService.sendEmail("Iytechli Şifre Yenileme",
                "Merhaba, " + name + " " + surname + "\nŞifreni yenilemek  için bu kodu kullanabilirsin: "
                        + refreshPasswordCode +"\n " , to );
    }
}
