package com.chuan.crawldata.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chuan.services.test.TestService;

@RestController
@RequestMapping("/test")
public class TestController {

    // @Autowired
    TestService test = new TestService();

    @GetMapping("/string")
    public String string() {
        return test.demo();
    }

    @GetMapping("")
    public ResponseEntity<?> story(@RequestParam(name = "name", defaultValue = "vu-than-chua-te") String name,
    @RequestParam(name = "chapter", defaultValue = "1") int chapter) {

        return ResponseEntity.ok(test.load(name, chapter));

    }
}
