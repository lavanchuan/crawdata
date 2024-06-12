package com.chuan.models.dtos;

import java.util.List;

import lombok.Data;

@Data
public class StoryDTO {
    protected String name;
    protected String author;
    protected String category;
    protected int totalChapter;
    protected List<StoryChapterDTO> chapters;
}
