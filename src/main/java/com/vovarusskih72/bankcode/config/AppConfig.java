package com.vovarusskih72.bankcode.config;

import com.vovarusskih72.bankcode.model.UserRoles;
import com.vovarusskih72.bankcode.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig extends GlobalMethodSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder () {return new BCryptPasswordEncoder();}

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("bankbotjava@gmail.com");
        mailSender.setPassword("bankbot1234567890");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public CommandLineRunner demo(final AccountService accountService,
                                  final PasswordEncoder encoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                accountService.addAccount("admin",
                        "Vova",
                        "Russkih",
                        encoder.encode("password"),
                        "+380687918242",
                        "vovarusskih72@gmail.com",
                        "1234",
                        UserRoles.ADMIN);
            }
        };
    }

}
