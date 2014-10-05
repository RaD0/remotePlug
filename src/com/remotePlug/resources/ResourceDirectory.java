package com.remotePlug.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class ResourceDirectory extends ResourceItem {

    private ArrayList<ResourceItem> children;

    public ResourceDirectory(UUID id, File mediaFile, String name, int size) {
        super(id, mediaFile, name, FileUtilities.ResourceType.Directory);
        children = new ArrayList<>(size);
    }

    void add(ResourceItem toAdd) {
        if(null != toAdd) children.add(toAdd);
    }

    public Collection<ResourceItem> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    public boolean hasChildren() {
        return (children.size() > 0);
    }

    public boolean hasChild(ResourceItem check) {
        return (null != check && hasChild(check.getId()));
    }

    public boolean hasChild(UUID id) {
        return (null != id && null != getChild(id));
    }

    public ResourceItem getChild(UUID id) {
        if (null == id) return null;
        for(ResourceItem child: children)
            if (child.match(id)) return child;
        return null;
    }

    public ResourceItem goDeepAndGetChild(UUID id) {
        if (null == id) return null;
        ResourceItem found = null;
        for(ResourceItem child: children) {
            if (child.getId().equals(id)) return child;
            if (FileUtilities.isADirectory(child)) {
                ResourceDirectory childDir = FileUtilities.toResourceDirectory(child);
                found = childDir.goDeepAndGetChild(id);
                if (null != found) return found;
            } else {
                found = getChild(id);
                if (null != found) return found;
            }
        }
        return found;
    }

    @Override
    public int compareTo(ResourceItem o) {
        if (null == o) return -1;
        if (getId().equals(o.getId())) return 0;
        return 1;
    }
}
