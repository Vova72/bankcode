package com.vovarusskih72.bankcode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {

    @RequestMapping(value = "/")
    public String getIndexPage() {
        return "redirect:/index.html";
    }

    @RequestMapping(value = "/unauthorized")
    public String unauthorizedPage() {
        return "redirect:/login.html";
    }

}
