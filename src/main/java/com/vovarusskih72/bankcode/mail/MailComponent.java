package com.vovarusskih72.bankcode.mail;

import com.vovarusskih72.bankcode.model.EmailSender;
import com.vovarusskih72.bankcode.repositories.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;

@Component
public class MailComponent {

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Scheduled(fixedDelay = 15000)
    public void allEmails() throws MessagingException {
        List<EmailSender> emailSenders = mailRepository.findAllEmails();
        for(EmailSender emailSender : emailSenders) {
            System.out.println(emailSender.toString());
            mailRepository.delete(emailSender);
            sendEmail(emailSender.getHost(), emailSender.getEmail(), emailSender.getActivateCode());
        }
    }

    public void sendEmail(String host, String email, String code) throws MessagingException {
        MimeMessage mm = mailSender.createMimeMessage();
        MimeMessageHelper msg = new MimeMessageHelper(mm);
        msg.setTo(email);

        String htmlText = "<a href='http://"+ host + "/activaccount?code=" + code + "'>Click here to activate account</a>";
        msg.setSubject("JavaBank account and wallet activation");
        msg.setText(htmlText, true);

        mailSender.send(mm);
    }
}
