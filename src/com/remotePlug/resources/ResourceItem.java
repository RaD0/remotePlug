package com.remotePlug.resources;

import com.remotePlug.handlers.Handler;
import com.remotePlug.handlers.HandlingData;
import com.remotePlug.handlers.PlugRequest;
import com.remotePlug.handlers.RequestHandler;

import java.io.File;
import java.util.UUID;

/**
 * A ResourceItem can either be a File or Folder.It provides basic details of it's type and other stuff.
 */

public abstract class ResourceItem implements Comparable<ResourceItem> {

    private UUID id;
    private File mediaFile;
    private String name;
    private FileUtilities.ResourceType type;
    private String format;
    private HandlingData handlingData;
    protected PlugRequest request;


    public ResourceItem(UUID id, File mediaFile, String name, FileUtilities.ResourceType type, String format) {
        this.id = id;
        this.mediaFile = mediaFile;
        this.name = name;
        this.type = type;
        this.format = format;
        handlingData = new HandlingData();
    }

    protected abstract void setPlugRequest();

    public UUID getId() {
        return id;
    }

    public String getPath() {
        return mediaFile.getAbsolutePath();
    }

    public String getName() {
        return name;
    }

    public File getRaw() {
        return mediaFile;
    }

    public FileUtilities.ResourceType getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }

    protected boolean match(UUID id) {
        return (null != id && getId().equals(id));
    }

    public HandlingData getHandlingData() {
        Handler handler = RequestHandler.getInstance().getHandler(request);
        if (null == handler) return handlingData;
        handler.packHandlingData(this, handlingData);
        return handlingData;
    }
}
