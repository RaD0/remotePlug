package com.remotePlug.resources;

import com.remotePlug.handlers.PlugRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * This is a direct map of a system folder/directory
 * An instance of ResourceDirectory can have children and a parent.
 * Children defining it sub-files/folders and parent defining it's root.
 * If it's the root directory, then parent would be null
 */
public class ResourceDirectory extends ResourceItem {

    private ArrayList<ResourceItem> children;
    private ResourceDirectory parent;

    public ResourceDirectory(UUID id, File mediaFile, String name, int size, ResourceDirectory parent) {
        super(id, mediaFile, name, FileUtilities.ResourceType.Directory, Format.Dir.toString());
        children = new ArrayList<>(size);
        this.parent = parent;
        setPlugRequest();
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

    public boolean hasParent() {
        return (null != parent);
    }

    public ResourceDirectory getParent() {
        return parent;
    }

    public ResourceItem getChild(UUID id) {
        if (null == id) return null;
        for(ResourceItem child: children)
            if (child.match(id)) return child;
        return null;
    }

    /**
     * Returns and item with the passed Id. It does a deep search,
     * Meaning if the item is present in the sub-sub-directory of this directory, even those items would be fetched
     * @param id
     * @return Returns if found the Resource item with id
     */
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

    // Seems like a really stupid thing to do!
    enum Format {
        Dir
    }

    @Override
    public int compareTo(ResourceItem o) {
        if (null == o) return -1;
        if (getId().equals(o.getId())) return 0;
        return 1;
    }

    @Override
    protected void setPlugRequest() {
        request = new PlugRequest(this, Format.Dir.toString());
    }
}
