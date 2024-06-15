package com.chuan.crawldata.story.models.threads;

import com.chuan.crawldata.story.crawl.CrawlerManager;
import com.chuan.crawldata.story.services.IOService;
import com.chuan.models.dtos.StoryChapterDTO;

import java.util.concurrent.TimeUnit;

public class LoadAndSaveFile extends Thread{

    IOService ioService = new IOService();

    CrawlerManager crawlerManager = new CrawlerManager();

    private String storyName;
    private int chapter;
    private long sleepTime = 30 * 1000;

    public LoadAndSaveFile(String storyName, int chapter){
        this.storyName = storyName;
        this.chapter = chapter;
    }

    @Override
    public void run() {
        super.run();

        //TODO SOMETHING
        //handler data
        StoryChapterDTO storyChapterDTO = loadChapter();

        //save....
        ioService.loadAndSaveFile(storyName, chapter, storyChapterDTO);
    }

    private StoryChapterDTO loadChapter() {
        StoryChapterDTO res;
         do {
             try {
                 res = crawlerManager.getCrawler().getChapter(storyName, chapter);
             } catch (Exception e) {
                 try {
                     System.err.println("ERROR: LOADING CHAPTER: " + chapter);
                     TimeUnit.MILLISECONDS.sleep(sleepTime);
                 } catch (Exception e2) {
                 }
//                 throw new RuntimeException();
                 res = null;
             }
         } while (res == null);

        System.out.println("SUCCESS: LOADED AND SAVED: " + storyName + "\t: " + chapter);
        return res;
    }
}
