package com.chuan.crawldata.story.services;

import org.springframework.stereotype.Service;

@Service
public class FormatService {
    public String formatName(String storyName) {
        if(storyName.contains(" ")) {
            return storyName.replaceAll(" ", "-");
        }

        return storyName;
    }

    public int getChapterFromChapterTitle(String chapter_title) {
        try {
            return Integer.parseInt(chapter_title.split(":")[0].split(" ")[1]);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
