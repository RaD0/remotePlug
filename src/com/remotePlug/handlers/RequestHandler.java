package com.remotePlug.handlers;

import com.remotePlug.resources.ResourceItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class RequestHandler {

    private LinkedList<Handler> handlers = new LinkedList<Handler>();
    private static RequestHandler instance = null;

    private RequestHandler() {}

    public static RequestHandler getInstance() {
        if(null == instance)
            instance = new RequestHandler();
        return instance;
    }

    public boolean handleMediaRequest(PlugRequest request) {
        Handler handler = getHandler(request);
        if (null == handler)  return false;
        handler.handle(request);
        return true;
    }

    public void registerHandler(Handler handlerToRegister) {
        if(null != handlerToRegister) handlers.add(handlerToRegister);
    }

    public Handler getHandler(PlugRequest request) {
        for(Handler handler: handlers) {
            if(handler.canHandle(request))
                return handler;
        }
        return null;
    }

    public Collection<ResourceItem> getRunningItems() {
        ArrayList<ResourceItem> items = new ArrayList<>();
        for(Handler handler: handlers) {
            ResourceItem item = handler.getCurrentlyHandling();
            if (null != item) items.add(item);
        }
        return items;
    }
}
