package com.remotePlug.plug.uiPlug.spark.routes;

import com.remotePlug.plug.uiPlug.spark.UISettings;
import com.remotePlug.plug.uiPlug.spark.controller.PlugController;

import static spark.Spark.get;

public class Routes {

    public static void ignite(UISettings uiSettings) {
        get("/list", PlugController::list, uiSettings.getFreeMarkerEngine());
        get("/item/:id/:action", PlugController::process);
        get("/dir/:id", PlugController::showDirectory, uiSettings.getFreeMarkerEngine());
        get("/item/:id", PlugController::showFile, uiSettings.getFreeMarkerEngine());
    }
}
