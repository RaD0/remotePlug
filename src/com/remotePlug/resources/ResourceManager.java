package com.remotePlug.resources;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.remotePlug.settings.ApplicationSettings;

import java.io.File;
import java.util.UUID;

public class ResourceManager {

    private static ResourceManager instance = null;
    TimeBasedGenerator idGenerator = Generators.timeBasedGenerator();
    private ResourceDirectory root = null;

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

    public ResourceDirectory getRoot() {
        return root;
    }

    public ResourceItem getItem(String uuid) {
        if(null == uuid) return null;
        return root.goDeepAndGetChild(UUID.fromString(uuid));
    }

    public void refreshList() {
        safeRefresh();
    }

    private void safeRefresh() {
//        HashMap<UUID,ResourceFile> backup = new HashMap<UUID,ResourceFile>(items);
//        items.clear();
//        if(!loadResources()) {
//            items =  backup;
//        } else {
//            backup.clear();
//        }
    }

    private boolean loadResources() {
        File root = ApplicationSettings.getInstance().getResourceRoot();
        if(null == root || !root.exists() || !root.isDirectory()) return false;
        try {
            this.root = createResourceDirectory(root, null);
            readAndCreateItems_(this.root);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void readAndCreateItems_(ResourceDirectory root) {
        if (null != root) {
            for(File file : root.getRaw().listFiles()) {
                if (file.isDirectory()) {
                    ResourceDirectory childDir = createResourceDirectory(file, root);
                    root.add(childDir);
                    readAndCreateItems_(childDir);
                } else {
                    root.add(createResourceFile(file, root));
                }
            }
        }
    }

    private ResourceDirectory createResourceDirectory(File file, ResourceDirectory parent) {
        String name = file.getName();
        if(null == name) return null;
        return new ResourceDirectory(idGenerator.generate(), file, name, file.list().length, parent);
    }

    private ResourceFile createResourceFile(File file, ResourceDirectory parent) {
        String name = FileUtilities.getFileName(file);
        if(null == name) return null;
        String format = FileUtilities.getFileFormat(file);
        if(null == format || !ApplicationSettings.getInstance().isValidFormat(format)) return null;
        return new ResourceFile(idGenerator.generate(), file, name, format, parent);
    }





}
