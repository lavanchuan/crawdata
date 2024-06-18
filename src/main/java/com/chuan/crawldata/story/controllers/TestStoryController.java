package com.chuan.crawldata.story.controllers;

import com.chuan.crawldata.story.services.IOService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;

@Controller
@RequestMapping("/story/test")
public class TestStoryController {

    @Autowired
    ServletContext servletContext;

    @GetMapping("/direct-exists")
    public ResponseEntity<?> directExists(@RequestParam(name = "path", defaultValue = "story") String path){
//        String fullPath = "src/main/resources/templates/" + path;
        String fullPath = path;
        try {
            File file = new File(fullPath);
            String res = "---";
            if(file.exists() && file.isDirectory()) {
                for(File f : file.listFiles()) {
                    if(f.isDirectory()) res = f.getName() + "\n" + res;
                    else res += "\n" + f.getName();
                }
            } else res = "exists";
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findPath(@RequestParam(name = "name") String name){
        List<String> res = new ArrayList<>();

        findPathMethod("/", name, res);

        return ResponseEntity.ok(res);
    }

    private void findPathMethod(String url, String name, List<String> res){
        try {
            File file = new File(url);
            String root = url;
            for(File f : file.listFiles()){
                if(!f.isDirectory()) {
                    if(name.equals(f.getName())) res.add(url);
                } else {
                    if(url.equals("/")) url = url + f.getName();
                    else url = url + "/" + f.getName();
                    findPathMethod(url, name, res);
                }
                url = root;
            }
            System.out.println(res);
        } catch (Exception e) {
            System.err.println("ERROR");
        }
    }

    @GetMapping("/file-create")
    public String createFile(@RequestParam(name = "fileName") String fileName,
                             @RequestParam(name = "isDirectory", defaultValue = "true") boolean isDirectory) {
//        String fullPath = "src/main/resources/templates/story/" + fileName;
        String fullPath = fileName;
        try {
            File file = new File(fullPath);
            if(file.exists()) return "exists";
            if(!isDirectory) {
                file.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write("This is new file");
                bw.write("\nAuthor: lavanchuan");
                bw.flush();
                bw.close();
                return "created-file";
            }
            file.mkdir();
            return "created-directory";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/show-file")
    public String showFile(@RequestParam(name = "fileName", defaultValue = "1.txt") String fileName){
//        String fullPath = "src/main/resources/templates/story/" + fileName;
        String fullPath = IOService.ROOT_SERVER +fileName;
        try {
            System.out.println("FULLPATH: " + fullPath);
            File file = new File(fullPath);
            System.out.println("FILE RESPONSE: " + IOService.ROOT_SERVER + fileName.substring(0, fileName.indexOf(".")));
            if(file.exists()) return IOService.ROOT_SERVER + fileName.substring(0, fileName.indexOf("."));
            else return "not-exists";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/get-path")
    public ResponseEntity<?> getPath(@RequestParam(name = "fileName") String fileName){
        // Lấy đường dẫn tương đối của file Main.java
        Path relativePath = Paths.get(fileName);

        System.out.println("Đường dẫn: " + relativePath);

        return ResponseEntity.ok(relativePath);
    }

    /*
    * GET FILE...
    */
    @GetMapping("/get-file")
    public ResponseEntity<?> getFile(@RequestParam(name = "file", defaultValue = "vu-than-chua-te/1.txt") String file){
        String fullPath = "/app/crawdata/story/";
        String res = "";
        try {
            File f = new File(fullPath + file);
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while((line = reader.readLine()) != null) res += line + "\n";
        }
        catch (Exception e) {
            res = "ERROR";
        }
        return ResponseEntity.ok(res);
    }

}
