package com.remotePlug.handlers;

import com.remotePlug.resources.ResourceItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Handles all the incoming requests.
 * The incoming requests can be from a HTTP server or Bluetooth server or from the command line
 */


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

    /**
     * Any handler which wishes to handle a certain request, should register itself here
     * @param handlerToRegister
     */

    public void registerHandler(Handler handlerToRegister) {
        if(null != handlerToRegister) handlers.add(handlerToRegister);
    }

    /**
     * Depending on the type of request, we loop through all the handlers and return the FIRST handler which
     * can handle the request
     * @param request
     * @return The matched plugin which can handle the request
     */

    public Handler getHandler(PlugRequest request) {
        for(Handler handler: handlers) {
            if(handler.canHandle(request))
                return handler;
        }
        return null;
    }

    /**
     * Each handler could be handling a resourceItem.
     * Like a VideoHandler is currently handling "video.avi" and DocumentHandler is handling "document.pdf"
     * This function returns all those "currently handling" item in a collection
     *
     * @return A collection of ResourceItems that are being currently handled by the handlers
     */
    public Collection<ResourceItem> getRunningItems() {
        ArrayList<ResourceItem> items = new ArrayList<>();
        for(Handler handler: handlers) {
            ResourceItem item = handler.getCurrentlyHandling();
            if (null != item) items.add(item);
        }
        return items;
    }
}
