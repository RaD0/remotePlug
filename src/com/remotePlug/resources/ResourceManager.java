package com.remotePlug.resources;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.remotePlug.settings.ApplicationSettings;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class ResourceManager {

    private static ResourceManager instance = null;
    private HashMap<UUID,ResourceMediaItem> items = new HashMap<UUID,ResourceMediaItem>();
    TimeBasedGenerator idGenerator = Generators.timeBasedGenerator();

    private ResourceManager() {
        loadResources();
    }

    static void init() {
        if(null == instance)
            instance = new ResourceManager();
    }

    public static ResourceManager getInstance() {
        return instance;
    }

    public Collection<ResourceMediaItem> getItems() {
        return Collections.unmodifiableCollection(items.values());
    }

    public ResourceMediaItem getItem(String uuid) {
        if(null == uuid) return null;
        return items.get(UUID.fromString(uuid));
    }

    public void refreshList() {
        safeRefresh();
    }

    private void safeRefresh() {
        HashMap<UUID,ResourceMediaItem> backup = new HashMap<UUID,ResourceMediaItem>(items);
        items.clear();
        if(!loadResources()) {
            items =  backup;
        } else {
            backup.clear();
        }
    }

    private boolean loadResources() {
        File root = ApplicationSettings.getInstance().getResourceRoot();
        if(null == root || !root.exists() || !root.isDirectory()) return false;
        try {
            readAndCreateItems_(root);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void readAndCreateItems_(File root) {
        for(File item: root.listFiles()) {
            if(item.isDirectory())
                readAndCreateItems_(item);
            else populateItems(item);
        }
    }

    private void populateItems(File itemToAdd) {
        if(null != itemToAdd) {
            UUID uuid = idGenerator.generate();
            String name = FileUtilities.getFileName(itemToAdd);
            if(null == name) return;
            String format = FileUtilities.getFileFormat(itemToAdd);
            if(null == format) return;
            ResourceMediaItem item_ = new ResourceMediaItem(uuid, itemToAdd, name, format);
            items.put(uuid, item_);
        }
    }





}
