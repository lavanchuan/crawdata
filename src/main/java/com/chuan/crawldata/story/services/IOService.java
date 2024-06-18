package com.chuan.crawldata.story.services;

import com.chuan.models.dtos.StoryChapterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@Service
public class IOService {

    public static final String ROOT_STORY_PATH = "src/main/resources/templates/story/";
    public static final String ROOT_SERVER = "/app/crawdata/story/";

    @Autowired
    FormatService formatService;

    public void initStoryDirect(){
        try {
            File file = new File(ROOT_SERVER);
            if(!file.exists()) file.mkdir();
            System.out.println("SUCCESS: Load story directory");
        } catch (Exception e) {
            System.err.println("ERROR: Load story directory");
        }
    }

    public boolean isExists(String storyName) {
        try {
//            File file = new File(ROOT_STORY_PATH + storyName);
            File file = new File(ROOT_SERVER + storyName);
            if(file.exists() && file.isDirectory()) return true;
            return false;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void createStoryDirect(String storyName) {
        try {
//            File file = new File(ROOT_STORY_PATH + storyName);
            File file = new File(ROOT_SERVER + storyName);
            file.mkdir();
            System.out.println("CREATE DIRECTORY: " + ROOT_SERVER + storyName);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void loadAndSaveFile(String storyName, int chapter, StoryChapterDTO data) {
        if(isChapterExists(storyName, chapter)) return;

//        loadData();
//        String fileSrc = ROOT_STORY_PATH + storyName + "/" + chapter + ".html";
        String fileSrc = ROOT_SERVER + storyName + "/" + chapter;
        try {
            writeDataFile(fileSrc, data);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void writeDataFile(String fileSrc, StoryChapterDTO data) throws RuntimeException{
        // write html file
        writeHtlmFile(fileSrc, data);
        // write txt file
        writeTextFile(fileSrc, data);
        // write json file
        writeJsonFile(fileSrc, data);
    }

    private void writeJsonFile(String fileSrc, StoryChapterDTO data) {
        try {
            File file = new File(fileSrc + ".json");
            if(file.exists()) return;
            file.createNewFile();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            String chapterNumber = String.format("chapterNumber: %d", data.getChapterNumber());
            String chapterTitle = String.format("chapterTitle: %s", data.getChapterTitle());
            String texts = "";

            for(int i = 0; i < data.getTexts().size(); i++){
                if(i == data.getTexts().size() - 1)
                    texts += "text: " + data.getTexts().get(data.getTexts().size() - 1);
                else texts += String.format("text: %s,\n", data.getTexts().get(i));
            }

            String dataFile = String.format("{%s,\n%s,\ntexts: [%s]}",
                    chapterNumber, chapterTitle, texts);

            bw.write(dataFile);
            bw.flush();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void writeTextFile(String fileSrc, StoryChapterDTO data) {
        try {
            File file = new File(fileSrc + ".txt");
            if(file.exists()) return;
            file.createNewFile();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            String dataFile = data.getChapterNumber() + "\n" + data.getChapterTitle();
            for(String text : data.getTexts()) dataFile += text + "\n";

            bw.write(dataFile);
            bw.flush();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void writeHtlmFile(String fileSrc, StoryChapterDTO data) {
        try {
            File file = new File(fileSrc + ".html");
            if(file.exists()) return;
            file.createNewFile();

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
//        String fileSrc = ROOT_STORY_PATH + storyName + "/" + chapter + ".html";
        String fileSrc = ROOT_SERVER + storyName + "/" + chapter + ".html";

        try {
            File file = new File(fileSrc);
            return file.exists();
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String getChapterFile(String name, int chapter) {
        return ROOT_SERVER + formatService.formatName(name) + "/" + chapter;
    }
}
