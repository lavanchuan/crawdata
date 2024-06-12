package com.chuan.crawldata.thu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/thu")
    String thuHome(){
        return "thu/index";
    }
}
