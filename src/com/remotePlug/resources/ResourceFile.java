package com.remotePlug.resources;

import com.remotePlug.handlers.PlugRequest;

import java.io.File;
import java.util.UUID;

public class ResourceFile extends ResourceItem {

    private ResourceDirectory parent;

    public ResourceFile(UUID id, File mediaFile, String name, String format, ResourceDirectory parent) {
        super(id, mediaFile, name, FileUtilities.ResourceType.File, format);
        this.parent = parent;
        setPlugRequest();
    }

    public ResourceDirectory getParent() {
        return parent;
    }

    public boolean hasParent() {
        return null != getParent();
    }

    @Override
    public String toString() {
        return getId()+" "+getName()+" "+getFormat();
    }

    @Override
    public int compareTo(ResourceItem o) {
        return 0;
    }

    @Override
    protected void setPlugRequest() {
        request = new PlugRequest(this, getFormat());
    }
}
