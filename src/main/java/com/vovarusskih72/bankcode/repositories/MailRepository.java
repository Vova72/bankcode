package com.vovarusskih72.bankcode.repositories;

import com.vovarusskih72.bankcode.model.EmailSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MailRepository extends JpaRepository<EmailSender, Long> {

    @Query("SELECT em FROM EmailSender em")
    public List<EmailSender> findAllEmails();

}
