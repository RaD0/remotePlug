package com.remotePlug.plugin.uiPlug.spark.routes;

import com.remotePlug.plugin.uiPlug.spark.UISettings;
import com.remotePlug.plugin.uiPlug.spark.controller.PlugController;

import static spark.Spark.get;
import static spark.Spark.post;

public class Routes {

    public static void ignite(UISettings uiSettings) {
        get("/list", PlugController::list, uiSettings.getFreeMarkerEngine());
        post("/play", PlugController::play, uiSettings.getFreeMarkerEngine());
    }
}
