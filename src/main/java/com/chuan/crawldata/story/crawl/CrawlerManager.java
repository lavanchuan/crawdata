package com.chuan.crawldata.story.crawl;


import lombok.Data;

@Data
public class CrawlerManager {
    ICrawler crawler;

    public CrawlerManager() {
        // DEFAULT JSOUP TOOL
        this.crawler = new JsoupCrawler();
    }

    public CrawlerManager(ICrawler crawler) {
        this.crawler = crawler;
    }
}
