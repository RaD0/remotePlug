package com.remotePlug.resources;

import java.io.File;
import java.util.UUID;

public class ResourceMediaItem implements Comparable<ResourceMediaItem> {

    private UUID id;
    private File mediaFile;
    private String name;
    private String format;

    public ResourceMediaItem(UUID id, File mediaFile, String name, String format) {
        this.id = id;
        this.mediaFile = mediaFile;
        this.name = name;
        this.format = format;
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

    public String getFormat() {
        return format;
    }

    public File getFile() {
        return mediaFile;
    }

    @Override
    public String toString() {
        return getId()+" "+getName()+" "+getFormat();
    }

    @Override
    public int compareTo(ResourceMediaItem o) {
        return 0;
    }
}
