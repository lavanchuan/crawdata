package com.chuan.crawldata.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chuan.models.JsoupCrawler;
import com.chuan.services.test.TestService;

@RestController
@RequestMapping("/test")
public class TestController {

    // @Autowired
    TestService test = new TestService();
    // @Autowired(required = true)
    JsoupCrawler jsoupCrawler = new JsoupCrawler();

    @GetMapping("/string")
    public String string() {
        return test.demo();
    }

    @GetMapping("")
    public ResponseEntity<?> story(@RequestParam(name = "name", defaultValue = "vu-than-chua-te") String name,
            @RequestParam(name = "chapter", defaultValue = "1") int chapter) {

        return ResponseEntity.ok(test.load(name, chapter));

    }

    @GetMapping("/last-chapter")
    public ResponseEntity<?> lastChapter(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(jsoupCrawler.getTotalChapter(name));
    }

    @GetMapping("/chapter")
    public ResponseEntity<?> chapter(@RequestParam(name = "name") String name,
            @RequestParam(name = "chapter") int chapter) {
        return ResponseEntity.ok(jsoupCrawler.getChapter(name, chapter));
    }

    @GetMapping("/name")
    public String getStoryName(@RequestParam(name = "name") String name) {
        return jsoupCrawler.getStoryName(name);
    }

    @GetMapping("/author")
    public String getStoryAuthor(@RequestParam(name = "name") String name) {
        return jsoupCrawler.getAuthorName(name);
    }

    @GetMapping("/category")
    public String getCategory(@RequestParam(name = "name") String name) {
        return jsoupCrawler.getCategory(name);
    }

    @GetMapping("/story")
    public ResponseEntity<?> story(@RequestParam(name = "name") String name,
    @RequestParam(name = "start", defaultValue = "1") int start,
    @RequestParam(name = "end", defaultValue = "10") int end) {
        return ResponseEntity.ok(jsoupCrawler.getStory(name, start, end));
    }
}
