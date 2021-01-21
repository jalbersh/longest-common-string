package com.jalbersh.lcs.utils;

import java.io.IOException;
import java.util.Properties;

public enum AppProperties {
    INSTANCE;

    private final Properties properties;

    private AppProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            System.err.println(e.getMessage()+e);
        }
    }

    public Properties getProperties() {return properties;}

    public String getLoggingFile() {
        return properties.getProperty("logging.file");
    }

    public String getAppName() {
        return properties.getProperty("app.name");
    }

    public String getUser() {
        return properties.getProperty("jwt.secret");
    }
}