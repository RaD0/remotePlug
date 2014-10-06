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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PlugController {

    private static Map<String, Object> attributes = new HashMap<>();

    static {
        attributes.put("helper", UIHelper.getInstance());
    }

    public static ModelAndView list(Request request, Response response) {
        ResourceDirectory root = ResourceManager.getInstance().getRoot();
        attributes.put("root", root);
        return new ModelAndView(attributes, "/plug/list.ftl");
    }

    public static Object process(Request request, Response response) {
        String id = request.params("id");
        String action = request.params("action");
        ResourceItem item = ResourceManager.getInstance().getItem(id);
        if (null != id && null != action) {
            PlugRequest plugRequest = new PlugRequest(item, action);
            RequestHandler.getInstance().handleMediaRequest(plugRequest);
        }
        response.redirect("/item/"+id);
        return null;
    }

    public static ModelAndView showDirectory(Request request, Response response) {
        String id = request.params("id");
        boolean added = false;
        if (null != id) {
            ResourceItem item = ResourceManager.getInstance().getItem(id);
            if (null != item && FileUtilities.isADirectory(item)) {
                attributes.put("root", FileUtilities.toResourceDirectory(item));
                added = true;
            }
        }
        if (!added)
            attributes.put("root", ResourceManager.getInstance().getRoot());
        return new ModelAndView(attributes, "/plug/list.ftl");
    }

    public static ModelAndView showFile(Request request, Response response) {
        String id = request.params("id");
        ResourceItem item = ResourceManager.getInstance().getItem(id);
        if (!FileUtilities.isAFile(item)) {
            response.redirect("/list");
            return null;
        }
        attributes.put("item", FileUtilities.toResourceFile(item));

        return new ModelAndView(attributes, "/plug/show.ftl");
    }

}
