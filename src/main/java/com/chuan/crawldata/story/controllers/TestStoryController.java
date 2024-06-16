package com.chuan.crawldata.story.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@Controller
@RequestMapping("/story/test")
public class TestStoryController {

    @GetMapping("/direct-exists")
    public String directExists(@RequestParam(name = "path", defaultValue = "story") String path){
        String fullPath = "src/main/resources/templates/" + path;
        try {
            File file = new File(fullPath);
            return file.exists() ? "exists" : "not-exists";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/file-create")
    public String createFile(@RequestParam(name = "fileName") String fileName) {
        String fullPath = "src/main/resources/templates/story/" + fileName;
        try {
            File file = new File(fullPath);
            if(file.exists()) return "exists";
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("This is new file");
            bw.write("\nAuthor: lavanchuan");
            bw.flush();
            bw.close();
            return "created-file";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/show-file")
    public String showFile(@RequestParam(name = "fileName") String fileName){
        String fullPath = "src/main/resources/templates/story/" + fileName;
        try {
            File file = new File(fullPath);
            if(file.exists()) return "story/" + fileName.substring(0, fileName.indexOf("."));
            else return "not-exists";
        } catch (Exception e) {
            return "error";
        }
    }

}
