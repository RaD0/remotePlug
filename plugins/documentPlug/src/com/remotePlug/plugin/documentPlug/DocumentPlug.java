package com.remotePlug.plugin.documentPlug;

import com.remotePlug.components.Plugin;
import com.remotePlug.handlers.RequestHandler;

public class DocumentPlug implements Plugin {

    @Override
    public boolean load() {
        RequestHandler.getInstance().registerHandler(new DocumentHandler());
        return true;
    }

    @Override
    public boolean unload() {
        return false;
    }
}
