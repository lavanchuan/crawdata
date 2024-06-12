package com.chuan.models;

import com.chuan.models.dtos.StoryChapterDTO;
import com.chuan.models.dtos.StoryDTO;

public interface Crawl {

    /*
     * return story name
     */
    String getStoryName(String name);

    /*
     * return category of story
     */
    String getCategory(String name);

    /*
     * return total chapter current
     */
    int getTotalChapter(String storyName);

    /*
     * return author
     */
    String getAuthorName(String storyName);

    /*
     * return chapter i
     */
    StoryChapterDTO getChapter(String name, int chapter);

    /*
     * return story full
     */
    StoryDTO getStory(String name);

    /*
     * return story with start chapter start to chapter end
     */
    StoryDTO getStory(String name, int start, int end);

}
