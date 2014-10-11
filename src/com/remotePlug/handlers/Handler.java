package com.remotePlug.handlers;

import com.remotePlug.resources.ResourceItem;

/**
 * The application receives different signals/messages.
 * These can be from a HTTP server or a bluetooth server or just simply form the command lines
 *
 * Handler defines a blue print of Handler which will handle different requests
 * Each handler should be specific and unique in their way to handling requests
 *
 * For instance, if there is an incoming request to handle mp4 formats, that a VideoHandler would take care of it.
 * Similarly to handle pdf formats, a DocumentHandler would take care of it and not a VideoHandler
 */

public interface Handler {
    public boolean canHandle(PlugRequest request);
    public void handle(PlugRequest request);
    public void packHandlingData(ResourceItem item, HandlingData handlingData);
    public ResourceItem getCurrentlyHandling();
}
