package com.chuan.services.format;

import org.springframework.stereotype.Service;

@Service
public class FormatService {

    public static final String PATTERN = "-";

    public String convertName(String name) {

        if (!name.contains(" ")) {
            return name;
        }

        String res = "";
        for(String item : name.split(" ")){
            res += item + PATTERN;
        }

        return res.substring(0, res.length()-1);
    }

    public int getChapterFromChapterTitle(String chapter_title) {
        try {
            return Integer.parseInt(chapter_title.split(":")[0].split(" ")[1]);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
