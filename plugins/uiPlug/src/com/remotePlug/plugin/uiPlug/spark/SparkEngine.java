package com.remotePlug.plugin.uiPlug.spark;

import com.remotePlug.plugin.uiPlug.spark.routes.Routes;
import spark.Spark;

public class SparkEngine {

    private static boolean running = false;

    public static boolean ignite() {
        if (running) return false;
        UISettings uiSettings = new UISettings();
        Spark.setPort(uiSettings.getPort());
        Spark.externalStaticFileLocation(uiSettings.getStaticContentPath().getAbsolutePath());
        Routes.ignite(uiSettings);
        running = true;
        return running;
    }

    public static boolean douse() {
        Spark.stop();
        running = false;
        return running;
    }
}
