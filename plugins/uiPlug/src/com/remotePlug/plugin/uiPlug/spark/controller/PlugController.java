package com.remotePlug.plugin.uiPlug.spark.controller;

import com.remotePlug.handlers.PlugRequest;
import com.remotePlug.handlers.RequestHandler;
import com.remotePlug.plugin.uiPlug.spark.helper.UIHelper;
import com.remotePlug.resources.FileUtilities;
import com.remotePlug.resources.ResourceDirectory;
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
        ResourceDirectory root = ResourceManager.getInstance().getRoot();
        attributes.put("root", root);
        attributes.put("helper", UIHelper.getInstance());
        return new ModelAndView(attributes, "/list.ftl");
    }

    public static Object process(Request request, Response response) {
        String id = request.params("id");
        String action = request.params("action");
        ResourceItem item = ResourceManager.getInstance().getItem(id);
        if (null != id && null != action) {
            PlugRequest plugRequest = new PlugRequest(item, action);
            RequestHandler.getInstance().handleMediaRequest(plugRequest);
        }
        response.redirect("/dir/"+FileUtilities.toResourceFile(item).getParent().getId());
        return null;
    }

    public static ModelAndView showDirectory(Request request, Response response) {
        String id = request.params("id");
        Map<String, Object> attributes = new HashMap<>();
        boolean added = false;
        if (null != id) {
            ResourceItem item = ResourceManager.getInstance().getItem(id);
            if (null != item && FileUtilities.isADirectory(item)) {
                attributes.put("root", FileUtilities.toResourceDirectory(item));
                added = true;
            }
        }
        attributes.put("helper", UIHelper.getInstance());
        if (!added)
            attributes.put("root", ResourceManager.getInstance().getRoot());
        return new ModelAndView(attributes, "/list.ftl");
    }

}
