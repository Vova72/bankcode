package com.vovarusskih72.bankcode.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EmailSender {

    @Id
    @GeneratedValue
    private Long id;

    private String host;
    private String email;
    private String activateCode;

    public EmailSender() {}

    public EmailSender(String host, String email, String activateCode) {
        this.host = host;
        this.email = email;
        this.activateCode = activateCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivateCode() {
        return activateCode;
    }

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode;
    }

    @Override
    public String toString() {
        return "EmailSender{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", email='" + email + '\'' +
                ", activateCode='" + activateCode + '\'' +
                '}';
    }
}
