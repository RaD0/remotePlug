package com.remotePlug.plug.uiPlug.spark;

import com.remotePlug.plug.uiPlug.spark.routes.Routes;
import org.apache.log4j.Logger;
import spark.Spark;

public class SparkEngine {

    private static boolean running = false;
    private static Logger logger = Logger.getLogger(SparkEngine.class);

    public static boolean ignite() {
        if (running) {
            logger.warn("Spark is already running. Not attempting to restart");
            return true;
        }
        UISettings uiSettings = new UISettings();
        Spark.setPort(uiSettings.getPort());
        Spark.setIpAddress(uiSettings.getHost());
        Spark.externalStaticFileLocation(uiSettings.getStaticContentPath().getAbsolutePath());
        Routes.ignite(uiSettings);
        running = true;
        logger.info("Spark is up are running");
        return running;
    }

    public static boolean douse() {
        Spark.stop();
        running = false;
        return running;
    }
}
