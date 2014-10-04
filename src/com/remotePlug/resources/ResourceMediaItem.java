package com.remotePlug.resources;

import java.io.File;

public class ResourceMediaItem implements Comparable<ResourceMediaItem> {

    private File mediaFile;
    private String name;
    private String format;

    public ResourceMediaItem(File mediaFile, String name, String format) {
        this.mediaFile = mediaFile;
        this.name = name;
        this.format = format;
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
        return getName();
    }

    @Override
    public int compareTo(ResourceMediaItem o) {
        return 0;
    }
}
