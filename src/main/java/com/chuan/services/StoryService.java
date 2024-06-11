package com.chuan.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StoryService {

    static final String PATTERN = "-";
    static final String CHAPTER_NAME = "chuong";
    static final String URL = "https://truyenfull.vn/";

    public ResponseEntity<?> getStoryChapter(String name, int chapter) {
        return ResponseEntity.ok("story/" + getNameStoryChapter(name, chapter));
    }

    private String getNameStoryChapter(String name, int chapter) {
        return String.format("%s/%s-%d", storyNameFormat(name), CHAPTER_NAME, chapter);
    }

    private String storyNameFormat(String name) {
        String res = "";
        for(String item : name.split(" ")) {
            res += item + PATTERN;
        }

        return res.substring(0, res.length()-1);
    }
}
