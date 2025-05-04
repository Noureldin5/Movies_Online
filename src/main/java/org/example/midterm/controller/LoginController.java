package org.example.midterm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @GetMapping("/")
    public @ResponseBody String home() {
        return "Welcome to MovieOnline API. Use API endpoints for authentication and operations.";
    }
}