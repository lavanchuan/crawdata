package com.chuan.crawldata.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chuan.services.StoryService;

@Controller
public class JsoupDemoController {

    // @Autowired
    // StoryService storyService;

    StoryService storyService = new StoryService();

    @GetMapping("/story")
    public ResponseEntity<?> storyPage(@RequestParam(name = "name") String name,
            @RequestParam(name = "chapter") int chapter) {
        return storyService.getStoryChapter(name, chapter);
    }

    @GetMapping("/name")
    public String chapter(@RequestParam(name = "name") String name,
            @RequestParam(name = "chapter") int chapter) {
            return "/story/"+name+"/chuong-"+chapter;
    }

}
