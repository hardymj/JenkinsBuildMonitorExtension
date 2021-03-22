package com.smartcodeltd.jenkinsci.plugins.buildmonitor.installation;

import com.smartcodeltd.jenkinsci.plugins.buildmonitor.BuildMonitorLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BuildMonitorBuildProperties {
    private static final BuildMonitorLogger logger = BuildMonitorLogger.forClass(BuildMonitorBuildProperties.class);

    private final Properties properties = new java.util.Properties();

    public BuildMonitorBuildProperties(String file) {
        try {
            InputStream in = getClass().getClassLoader().getResourceAsStream(file);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            logger.warning("constructor", "Build Monitor couldn't load its properties from {0}: {1}", file, e.getMessage());
        }
    }

    public String get(String name) {
        return properties.getProperty(name, "unknown");
    }
}