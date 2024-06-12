package com.chuan.models.dtos;

import java.util.List;

import lombok.Data;

@Data
public class StoryChapterDTO {

    private int chapterNumber;
    private String chapterTitle;
    private List<String> texts;
}
