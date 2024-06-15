package com.chuan.crawldata.app.controllers;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    boolean running;
    AppRun app;

    @GetMapping("/start-test")
    String startTest(){
        return "start-test";
    }

    @GetMapping("/start")
    String start() {
        if (!running) {
            running = true;
            app = new AppRun();
            app.start();
        }

        return "start";
    }

    @GetMapping("/stop")
    String stop() {
        if (running)
            running = false;
        return "stop";
    }

    class AppRun extends Thread {
        @Override
        public void run() {
            super.run();
            do {
                try {
                    while (running) {
                        System.out.println("APP RUNNING");
                        TimeUnit.MILLISECONDS.sleep(5000);
                    }
                } catch (Exception e) {
                    System.err.println("ERROR RUNNING");
                }
            } while (running);
        }
    }
}
