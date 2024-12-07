package com.example.gymprogress_tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping("/about")
    public String about() {
        return "about"; // Verwijst naar de "about.html"-pagina
    }
}
