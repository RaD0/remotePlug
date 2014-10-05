package com.remotePlug.resources;

import java.io.File;
import java.util.UUID;

public abstract class ResourceItem implements Comparable<ResourceItem> {

    private UUID id;
    private File mediaFile;
    private String name;
    private FileUtilities.ResourceType type;

    public ResourceItem(UUID id, File mediaFile, String name, FileUtilities.ResourceType type) {
        this.id = id;
        this.mediaFile = mediaFile;
        this.name = name;
        this.type = type;
    }

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

    protected boolean match(UUID id) {
        return (null != id && getId().equals(id));
    }
}
