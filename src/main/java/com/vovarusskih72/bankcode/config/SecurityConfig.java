package com.vovarusskih72.bankcode.config;

import com.vovarusskih72.bankcode.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService accountDetailsService;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(accountDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/newpassword.html", "/resetpassword.html", "/setnewpassword", "/checkaccountemail", "/login.html", "/register.html", "/logout", "/js/**", "/newuser", "/activaccount", "/css/**", "/accountcheck")
                    .permitAll()
                .anyRequest()   
                    .hasAnyRole("USER", "ADMIN", "MODERATOR")
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/unauthorized")
                .and()
                .formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/j_spring_security_check")
                    .failureUrl("/login.html?error=password")
                    .usernameParameter("j_login")
                    .passwordParameter("j_password")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login.html");
    }
}
