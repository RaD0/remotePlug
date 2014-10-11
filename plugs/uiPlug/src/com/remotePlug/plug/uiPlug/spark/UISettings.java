package com.remotePlug.plug.uiPlug.spark;

import com.remotePlug.settings.ApplicationSettings;
import freemarker.template.Configuration;
import org.apache.log4j.Logger;
import org.ini4j.Profile;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.io.IOException;

public class UISettings {

    private Profile.Section settings;
    private Configuration freeMarkerConfiguration;
    private Logger logger = Logger.getLogger(UISettings.class);

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
            logger.error("Failed to get port for Spark. Setting it to default value: "+DEFAULT_PORT);
            return DEFAULT_PORT;
        }
    }

    public File getStaticContentPath() {
        final File DEFAULT_PATH = new File("static");
        File customPath;
        try {
            customPath = new File(settings.get("staticContentPath"));
        } catch (Exception e) {
            logger.error("Failed to get StaticContentPath", e);
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
        // A really stupid thing to give absolute path
        final File views = new File("/home/RaD0/IdeaProjects/remoteKonect/plugs/uiPlug/src/com/remotePlug/plug/uiPlug/spark/view");
        Configuration configuration = new Configuration();
        try {
            configuration.setDirectoryForTemplateLoading(views);
        } catch (IOException e) {
            logger.error("Failed to create FreeMarker configuration", e);
            return null;
        }
        return configuration;
    }

}
