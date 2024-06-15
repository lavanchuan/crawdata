package com.chuan.crawldata.story.services;

import com.chuan.crawldata.story.crawl.CrawlerManager;
import com.chuan.crawldata.story.models.requests.StoryLoadRequest;
import com.chuan.crawldata.story.models.threads.LoadAndSaveFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class StoryService {

    @Autowired
    FormatService formatService;

    @Autowired
    IOService ioService;

    //    @Autowired
    CrawlerManager crawlerManager = new CrawlerManager();

    public ResponseEntity<?> loadStory(StoryLoadRequest request) {
        System.out.println("LOADING STORY");
        String storyName = formatService.formatName(request.getStoryName());
        if (request.getChapterEnd() == 0 && request.getChapterCount() == 0)
            request.setChapterEnd(crawlerManager.getCrawler().getStoryLastChapter(request.getStoryName()));
        else if (request.getChapterEnd() == 0)
            request.setChapterEnd(request.getChapterStart() + request.getChapterCount());

        if (!ioService.isExists(storyName)) ioService.createStoryDirect(storyName);

        for (int i = request.getChapterStart(); i <= request.getChapterEnd(); i++) {
            LoadAndSaveFile thread = new LoadAndSaveFile(storyName, i);
            thread.start();
        }

        return ResponseEntity.ok("//TODO SOMETHING");
    }

    public String getChapter(String name, int chapter) {
        name = formatService.formatName(name);
        if (ioService.isChapterExists(name, chapter))
            return ioService.getChapterFile(name, chapter);

        ///TODO load chapter request
        ioService.loadAndSaveFile(name, chapter, crawlerManager.getCrawler().getChapter(name, chapter));

//        StoryLoadRequest request = new StoryLoadRequest();
//        request.setStoryName(name);
//        loadStory(request);

        return "story/chapter-await";
    }
}
