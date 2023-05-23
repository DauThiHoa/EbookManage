package com.ManageBookStore.ManageBookStore.controller;
import com.ManageBookStore.ManageBookStore.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class GreetingController {

    @Autowired
    private MessageSource messageSource;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/greeting")
    public String getGreeting(Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String greeting = messageSource.getMessage("greeting", null, LocaleContextHolder.getLocale());
        log.info("=== GREETING ===== + " + greeting);
        model.addAttribute("greeting", greeting);
        return "greeting";
    }

}