package com.remotePlug.plugin.uiPlug.spark;

import com.remotePlug.settings.ApplicationSettings;
import freemarker.template.Configuration;
import org.ini4j.Profile;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.io.IOException;

public class UISettings {

    private Profile.Section settings;
    private Configuration freeMarkerConfiguration;

    UISettings() {
        settings = ApplicationSettings.getInstance().getSettings("UI");
        freeMarkerConfiguration = getFreeMarkerConfiguration();
    }

    public String getHost() {
        final String DEFAULT_HOST = "127.0.0.1";
        return (null == settings) ? DEFAULT_HOST : (null == settings.get("host")) ? DEFAULT_HOST : settings.get("host");
    }

    public int getPort() {
        final int DEFAULT_PORT = 8080;
        int port;
        try {
            port = Integer.parseInt(settings.get("port"));
            return (port <= 0) ? DEFAULT_PORT : port;
        } catch (Exception e) {
            return DEFAULT_PORT;
        }
    }

    public File getStaticContentPath() {
        final File DEFAULT_PATH = new File("static");
        File customPath;
        try {
            customPath = new File(settings.get("staticContentPath"));
        } catch (Exception e) {
            customPath = null;
        }
        return (null != customPath && customPath.exists() && customPath.isDirectory()) ? customPath : DEFAULT_PATH;
    }

    public FreeMarkerEngine getFreeMarkerEngine() {
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        if (null != freeMarkerConfiguration)
            freeMarkerEngine.setConfiguration(freeMarkerConfiguration);
        return freeMarkerEngine;
    }

    private Configuration getFreeMarkerConfiguration() {
        final File views = new File("/home/RaD0/IdeaProjects/remoteKonect/plugins/uiPlug/src/com/remotePlug/plugin/uiPlug/spark/view");
        Configuration configuration = new Configuration();
        try {
            configuration.setDirectoryForTemplateLoading(views);
        } catch (IOException e) {
            return null;
        }
        return configuration;
    }

}
