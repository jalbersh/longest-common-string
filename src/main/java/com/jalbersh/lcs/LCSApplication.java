package com.jalbersh.lcs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LCSApplication {

    private static final Logger logger = LoggerFactory.getLogger(LCSApplication.class);

    public static void main(String[] args) {
        printArguments(args);
        SpringApplication.run(LCSApplication.class, args);
    }

    /**
     * Method to print arguments
     *
     * @param args
     */
    public static void printArguments(String[] args) {
        logger.info("LCSApplication startup - printing all arguments");
        for (String arg : args) {
            logger.info(arg);
        }
    }
}
