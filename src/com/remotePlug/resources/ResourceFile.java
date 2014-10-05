package com.remotePlug.resources;

import java.io.File;
import java.util.UUID;

public class ResourceFile extends ResourceItem {

    private String format;
    private ResourceDirectory parent;

    public ResourceFile(UUID id, File mediaFile, String name, String format, ResourceDirectory parent) {
        super(id, mediaFile, name, FileUtilities.ResourceType.File);
        this.format = format;
        this.parent = parent;
    }

    public String getFormat() {
        return format;
    }

    public ResourceDirectory getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return getId()+" "+getName()+" "+getFormat();
    }

    @Override
    public int compareTo(ResourceItem o) {
        return 0;
    }
}
