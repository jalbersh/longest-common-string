package com.jalbersh.lcs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class PingController {

    private static final Logger logger = LoggerFactory.getLogger(PingController.class);

    /**
     * This rest method will return whether the application is available
     *
     * @return String Ping Status
     */
    @GetMapping("/ping")
    public String ping() {
        logger.info("Ping Controller.ping invoked");
        return "Ping successful. Server Time: " + new Date().toString();
    }
}
