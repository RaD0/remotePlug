package com.remotePlug.handlers;

import com.remotePlug.resources.ResourceMediaItem;

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

    public boolean handleMediaRequest(ResourceMediaItem requestedMedia) {
        for(Handler handler: handlers) {
            if(handler.canHandle(requestedMedia)) {
                handler.handle(requestedMedia);
                return true;
            }
        }
        return false;
    }

    public void registerHandler(Handler handlerToRegister) {
        if(null != handlerToRegister) handlers.add(handlerToRegister);
    }
}
