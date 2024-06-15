package com.chuan.crawldata.story.crawl;

import com.chuan.crawldata.story.configs.AppData;
import com.chuan.crawldata.story.services.FormatService;
import com.chuan.models.dtos.StoryChapterDTO;
import com.chuan.models.dtos.StoryDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

//@Service
public class JsoupCrawler implements ICrawler{

//    @Autowired
    FormatService formatService = new FormatService();

    @Override
    public String getStoryName(String name) {
        return null;
    }

    @Override
    public String getStoryAuthor(String name) {
        return null;
    }

    @Override
    public int getStoryLastChapter(String name) {
        try {
            String web = AppData.ROOT_WEB;
            String url = web + formatService.formatName(name);

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
    public String getStoryCategory(String name) {
        return null;
    }

    @Override
    public StoryChapterDTO getChapter(String name, int chapter) {
        name = formatService.formatName(name);
        String url = AppData.ROOT_WEB + name + "/" + AppData.CHAPTER_NAME + AppData.PATTERN + chapter;

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
    public List<StoryChapterDTO> getChapterList(String name, int chapterStart, int chapterEnd) {
        return null;
    }

    @Override
    public StoryDTO getStory(String name) {
        return null;
    }
}
