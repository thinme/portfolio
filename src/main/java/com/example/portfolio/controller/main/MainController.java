package com.example.portfolio.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class MainController {

    @GetMapping("/main/getMainList")
    public String getWebMainList(){
        return "/main/getMainList";
    }
}
