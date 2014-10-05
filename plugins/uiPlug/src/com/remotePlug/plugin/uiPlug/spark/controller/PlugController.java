package com.remotePlug.plugin.uiPlug.spark.controller;

import com.remotePlug.handlers.PlugRequest;
import com.remotePlug.handlers.RequestHandler;
import com.remotePlug.plugin.uiPlug.spark.helper.UIHelper;
import com.remotePlug.resources.ResourceItem;
import com.remotePlug.resources.ResourceManager;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PlugController {

    public static ModelAndView list(Request request, Response response) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("items", ResourceManager.getInstance().getRoot().getChildren());
        attributes.put("helper", UIHelper.getInstance());
        return new ModelAndView(attributes, "/list.ftl");
    }

    public static ModelAndView play(Request request, Response response) {
        String id = request.queryParams("id");
        ResourceItem item = ResourceManager.getInstance().getItem(id);
        PlugRequest plugRequest = new PlugRequest(item, "Play");
        RequestHandler.getInstance().handleMediaRequest(plugRequest);
        return list(request, response);
    }

}
