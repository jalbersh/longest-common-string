package com.jalbersh.lcs.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Loggers {
    //This is used to log main steps of the overall service
    public static final Logger main = LoggerFactory.getLogger("main");

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd-HHmmss");

    private static String getDtoMessage(Object dto) {
        // Originally created an interface and made it all object oriented and stuff
        // But this keeps is simple and all in one place
        String action = "io performed for";
        return action + " " + getDebugName(dto);
    }

    private static String getDebugName(Object dto) {
        // Originally created an interface and made it all object oriented and stuff
        // But this keeps is simple and all in one place
        return dto.getClass().getName();
    }

    /**
     * logs simple message at INFO level
     * logs requests and responses if DEBUG level
     * if log level is TRACE writes requests and responses to a directory based on the request timestamp
     *
     * @param logger
     * @param timestamp
     * @param dto
     */
    public static void logIo(Logger logger, Timestamp timestamp, Object dto) {
        logger.info(getDtoMessage(dto));
        String jsonString = null;
        if (logger.isDebugEnabled()) {
            jsonString = jsonString(dto);
            logger.debug(jsonString);
        }
        if (logger.isTraceEnabled()) {
            try {
                PrintWriter out = new PrintWriter(createTimestampDir(timestamp) + getDebugName(dto) + ".json");
                out.print(jsonString);
            } catch (Throwable e) {
                //Don't ever want logging to throw errors
                logger.error("Unable to run logIo");
                e.printStackTrace();
            }
        }
    }

    private static String createTimestampDir(Timestamp timestamp) throws IOException {
        String formattedTime = timestamp.toLocalDateTime().format(formatter);
        String dirName = "target/" + formattedTime;
        Files.createDirectories(Paths.get(dirName));
        return dirName;
    }

    public static void jsonToFile(Object ob, String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), ob);
        } catch (IOException e) {
            System.out.println("Can't serialize: " + ob);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String jsonString(Object ob) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ob);
        } catch (IOException e) {
            System.out.println("Can't serialize: " + ob);
            e.printStackTrace();
            return "Can't serialize: " + ob;
        }
    }
}
