package com.chuan.crawldata.story.models.requests;

import lombok.Data;

@Data
public class StoryLoadRequest {
    private String storyName;
    private int chapterStart = 1; //default
    private int chapterEnd;
    private int chapterCount;
}
