package com.chuan.crawldata.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chuan.services.LoadStoryService;

@Controller
public class HomeController {

    // ENVIRONMENT VARIABLES
    static final String[] STORYS = {"vu than chua te"};

    // INJECTION
    LoadStoryService storyService = new LoadStoryService();

//    @GetMapping("/home")
//    ResponseEntity<?> homepage() {
//        loadStory();
//
//        // loadStoryDefault();
//
//        System.out.println("Load story success");
//
//        // return ResponseEntity.ok("homepage");
//        // return ResponseEntity.ok("story/vu-than-chua-te/a");
//        return ResponseEntity.ok("home");
//    }

    /***
     * 
     */
    @GetMapping("/string")
    public String string() {
        return "string";
    }

    //


    private void loadStoryDefault() {
        for(String name : STORYS) {
            storyService.loadStory(name);
        }
    }

    @GetMapping("/load-story")
    ResponseEntity<?> loadStory() {
        return storyService.loadStory();
    }

}
