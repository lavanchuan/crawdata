package com.chuan.crawldata.story.services;

import com.chuan.models.dtos.StoryChapterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@Service
public class IOService {

    static final String ROOT_STORY_PATH = "src/main/resources/templates/story/";

    @Autowired
    FormatService formatService;

    public boolean isExists(String storyName) {
        try {
            File file = new File(ROOT_STORY_PATH + storyName);
            if(file.exists() && file.isDirectory()) return true;
            return false;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void createStoryDirect(String storyName) {
        try {
            File file = new File(ROOT_STORY_PATH + storyName);
            file.mkdir();
            System.out.println("CREATE DIRECTORY: " + storyName);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void loadAndSaveFile(String storyName, int chapter, StoryChapterDTO data) {
        if(isChapterExists(storyName, chapter)) return;

//        loadData();
        String fileSrc = ROOT_STORY_PATH + storyName + "/" + chapter + ".html";
        try {
            File file = new File(fileSrc);
            writeFile(file, data);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void writeFile(File file, StoryChapterDTO data) {
        try {
            String head, body;
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            // header
            head = "<head><link href='story/css/style.css' /></head>\n";
            // body
            body = "<h1>"+data.getChapterTitle()+"</h1>";
            for(String p : data.getTexts()) body += "\n<p>" + p + "</p>";
            body = "<body>"+body+"</body>";
            bw.write(head + body);
            bw.flush();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public boolean isChapterExists(String storyName, int chapter) {
        String fileSrc = ROOT_STORY_PATH + storyName + "/" + chapter + ".html";

        try {
            File file = new File(fileSrc);
            return file.exists();
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String getChapterFile(String name, int chapter) {
        return "story/" + formatService.formatName(name) + "/" + chapter;
    }
}
