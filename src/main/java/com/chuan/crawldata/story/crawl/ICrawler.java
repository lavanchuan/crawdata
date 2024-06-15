package com.chuan.crawldata.story.crawl;

import com.chuan.models.dtos.StoryChapterDTO;
import com.chuan.models.dtos.StoryDTO;

import java.util.List;

public interface ICrawler {

    String getStoryName(String name);

    String getStoryAuthor(String name);

    int getStoryLastChapter(String name);

    String getStoryCategory(String name);

    StoryChapterDTO getChapter(String name, int chapter);

    List<StoryChapterDTO> getChapterList(String name, int chapterStart, int chapterEnd);

    StoryDTO getStory(String name);
}
