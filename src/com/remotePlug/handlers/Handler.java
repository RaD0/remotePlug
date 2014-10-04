package com.remotePlug.handlers;

public interface Handler {
    public boolean canHandle(PlugRequest request);
    public void handle(PlugRequest request);
}
