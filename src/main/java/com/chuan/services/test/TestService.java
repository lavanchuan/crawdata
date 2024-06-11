package com.chuan.services.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    final String URL = "https://truyenfull.vn";
    final String CHAPTER_NAME = "chuong";
    final String PATTERN = "-";
    final String PATH = "src/main/resources/templates/story/";

    public String demo() {
        return "demo";
    }

    public String load(String name, int chapter) {

        String url = PATH + name;

        try {
            File file = new File(url);
            if (!file.exists()) file.mkdir();
            return loadChapter(name, chapter);
        } catch (Exception e) {
            return "error";
        }
    }

    private String loadChapter(String name, int chapter) {
        Document doc;
        Elements elements;
        String url = String.format("%s/%s/%s%s%d", URL,name,CHAPTER_NAME,PATTERN, chapter);
        String res = "";
        try {
            doc = Jsoup.connect(url).get();

            elements = doc.select("p");

            elements.add(0, doc.select("a[class='chapter-title']").get(0)
            .removeAttr("href"));
   
            res = new Elements(elements.subList(0, elements.size()-1)).outerHtml();

            saveFile(name, chapter, res);

            return res;

            // for(Element e : doc.select("p")) {
            //     data.append(e.text() + "\n");
            // }

            // return data.toString();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void saveFile(String name, int chapter, String data) {
        File file;
        try {
            file = new File(PATH + name + "/" + chapter + ".html");
            if(!file.exists()) file.createNewFile();
            writeFile(file, data, getHead(name, chapter));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private String getHead(String name, int chapter) {
        return "<head><link href='css/style.css'></head>";
    }

    private void writeFile(File file, String data, String header) {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(file));

            data = "<body>" + data + "</body>";
            data = header + data;

            bw.write(data);
            bw.flush();

            bw.close();
        } catch(Exception e) {
            
            throw new RuntimeException();
        }
  
    }
}
