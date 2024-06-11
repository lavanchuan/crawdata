package com.chuan.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoadStoryService {

    static final String PATTERN = "-";
    static final String CHAPTER_NAME = "chuong";
    static final String URL = "https://truyenfull.vn/";

    public ResponseEntity<?> loadStory() {

        return null;
        
    }

    public ResponseEntity<?> loadStory(String name) {

        System.out.println("Loading: " + name);

        // load chapter 1
        loadChapter(name, 1);

        System.out.println("Loaded: " + name);

        return null;
    }

    private void loadChapter(String name, int chapter) {

        String url = URL + nameFormat(name) + "/" +CHAPTER_NAME + "-" + chapter;
        String dir = "templates/story/" + nameFormat(name);
        Path path = Paths.get(dir);
        if(!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                System.out.println("Created diectory " + dir);
            } catch (Exception e) {};
        }

        try {
            Document doc = Jsoup.connect(url).get();
            File file = new File("templates/story/" + nameFormat(name) + "/" + chapter + ".html");
            if(file.exists()) {} 
            else {
                file.createNewFile();
            }
        } catch(Exception e) {
            throw new RuntimeException();
        }
        
    }

    private String nameFormat(String name) {
        String res = "";

        for(String item : name.split(" ")) res += item + CHAPTER_NAME;

        return res.substring(0, res.length()-1);
    }


}
