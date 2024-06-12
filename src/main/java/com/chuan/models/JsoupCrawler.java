package com.chuan.models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.chuan.AppData;
import com.chuan.models.dtos.StoryChapterDTO;
import com.chuan.models.dtos.StoryDTO;
import com.chuan.services.format.FormatService;

public class JsoupCrawler implements Crawl {

    // @Autowired
    FormatService formatService = new FormatService();

    @Override
    public int getTotalChapter(String storyName) {
        try {
            String web = AppData.WEB;
            String url = web + formatService.convertName(storyName);

            Document doc = Jsoup.connect(url).get();

            Element pagination = doc.selectFirst(".pagination");
            Elements pages = pagination.select("a");
            Element pageLast = pages.get(pages.size() - 2);
            String urlPageLast = pageLast.attr("href");

            //
            doc = Jsoup.connect(urlPageLast).get();
            Element menu = doc.select(".row").get(1);
            Elements chapterList = menu.select("li");
            Element lastChapter = chapterList.last();

            int chapter = formatService.getChapterFromChapterTitle(lastChapter.text());

            return chapter;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public String getAuthorName(String storyName) {
        String url = AppData.WEB + formatService.convertName(storyName);

        try {

            Document doc = Jsoup.connect(url).get();

            Element title = doc.selectFirst("a[itemprop='author']");

            return title.text();

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public StoryChapterDTO getChapter(String name, int chapter) {
        name = formatService.convertName(name);
        String url = AppData.WEB + name + "/" + AppData.CHAPTER_NAME + AppData.PATTERN + chapter;

        try {

            Document doc = Jsoup.connect(url).get();

            StoryChapterDTO res = new StoryChapterDTO();
            res.setChapterNumber(chapter);

            String chapterTitle = doc.selectFirst(".chapter-title").text();
            res.setChapterTitle(chapterTitle);

            Elements chapterText = doc.selectFirst("#chapter-c").select("p");
            List<String> texts = new ArrayList<>();
            for (Element e : chapterText) {
                texts.add(e.text());
            }
            res.setTexts(texts);

            return res;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public StoryDTO getStory(String name) {
        try {
            StoryManager story = new StoryManager();
            story.setName(getStoryName(name));
            story.setAuthor(getAuthorName(name));
            story.setCategory(getCategory(name));
            story.setTotalChapter(getTotalChapter(name));

            for(int i = 1; i <= story.getTotalChapter(); i++) {
                story.addChapter(getChapter(name, i));
            }

            return story;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    // ...
    @Override
    public StoryDTO getStory(String name, int start, int end) {
        long startTime = System.currentTimeMillis();
        long endTime;
        try {
            StoryManager story = new StoryManager();
            story.setName(getStoryName(name));
            story.setAuthor(getAuthorName(name));
            story.setCategory(getCategory(name));
            story.setTotalChapter(getTotalChapter(name));

            for(int i = start; i <= end; i++) {
                try {
                    story.addChapter(getChapter(name, i));
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (Exception e){
                    TimeUnit.MILLISECONDS.sleep(2000);
                    i--;
                }
            }

            endTime = System.currentTimeMillis();

            System.out.println("TIMER: " + (endTime - startTime));

            return story;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public String getStoryName(String name) {
        String url = AppData.WEB + formatService.convertName(name);

        try {

            Document doc = Jsoup.connect(url).get();

            Element title = doc.selectFirst("h3[itemprop='name']");

            return title.text();

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public String getCategory(String name) {
        String url = AppData.WEB + formatService.convertName(name);

        try {

            Document doc = Jsoup.connect(url).get();

            Element title = doc.selectFirst("a[itemprop='genre']");

            return title.text();

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
