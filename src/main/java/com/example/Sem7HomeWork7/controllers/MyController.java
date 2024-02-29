package com.example.Sem7HomeWork7.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    /**
     * Домашняя страница
     * @return файл домашней страницы
     */
    @GetMapping("/")
    public String home(){
        return "home_page";
    }

    /**
     * Страница для пользователей с ролью USER
     * @return публичное представление
     */
    @GetMapping("/public-data")
    public String userPage(){
        return "public_page";
    }

    /**
     * Страница для пользователей с ролью ADMIN
     * @return закрытое представление
     */
    @GetMapping("/private-data")
    public String adminPage(){
        return "private_page";
    }

    /**
     * Аутентификация пользователя
     * @return представление аутентификации
     */
    @GetMapping("/login")
    public String auth(){
        return "login_page";
    }
}
