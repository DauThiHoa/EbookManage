package com.ManageBookStore.ManageBookStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Controller
public class LanguageController {

//    @GetMapping("/change-language")
//    public String changeLanguage(@RequestParam("lang") String lang, HttpServletRequest request, HttpServletResponse response) {
//        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
//        request.setCharacterEncoding("UTF-8");
//        localeResolver.setLocale(request, response, new Locale(lang));
//        return "redirect:/";
//    }
    @GetMapping("/change-language")
    public String changeLanguage(@RequestParam("lang") String lang, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        localeResolver.setLocale(request, response, new Locale(lang));
        return "redirect:/";
    }
}