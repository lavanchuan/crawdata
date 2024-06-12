package com.chuan.models;

import java.util.ArrayList;

import com.chuan.models.dtos.StoryChapterDTO;
import com.chuan.models.dtos.StoryDTO;

public class StoryManager extends StoryDTO{

    public StoryManager() {
        super();
        this.chapters = new ArrayList<>();
    }

    public void addChapter(StoryChapterDTO chapter) {
        this.chapters.add(chapter);
    }

}
