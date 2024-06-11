package com.chuan.crawldata.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/test/page")
    String page() {
        return "story/vu-than-chua-te/1";
    }
}
