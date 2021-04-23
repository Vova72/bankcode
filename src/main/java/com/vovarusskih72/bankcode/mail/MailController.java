package com.vovarusskih72.bankcode.mail;

import com.vovarusskih72.bankcode.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MailController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/activaccount")
    public String activateAccount(@RequestParam("code") String code) {
        
        accountService.activateAccount(code);
        return "redirect:/login.html";
    }
}
