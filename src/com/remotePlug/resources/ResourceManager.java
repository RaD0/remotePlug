package com.remotePlug.resources;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.remotePlug.settings.ApplicationSettings;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.UUID;

/**
 * Behaves as file manager.
 * Fetches the source directory from the settings and adds all of it's sub-directories and sub-files as it's
 * subsequent children. The directory is mapped to a custom implemented tree structure
 *
 * A directory/folder in the system is mapped to ResourceDirectory in the application
 * A file in the system in mapped to ResourceFile in application
 *
 * Each ResourceDirectory/File is stored in the application memory, with an UUID acting as it's ID
 */

public class ResourceManager {

    private static ResourceManager instance = null;
    TimeBasedGenerator idGenerator = Generators.timeBasedGenerator();
    private ResourceDirectory root = null;
    private Logger logger = Logger.getLogger(ResourceManager.class);

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
        if(null == uuid) {
            logger.error("Null UUID requested to fetch ResourceItem");
            return null;
        }
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

    /**
     * Each Folder is ResourceDirectory. If this has sub-items, then they are added as children to the ResourceDirectory.
     * The children can either be files or directories
     * @return True is all the files and folders are loaded and mapped to ResourceDirectory/File
     */
    private boolean loadResources() {
        File root = ApplicationSettings.getInstance().getResourceRoot();
        if(null == root || !root.exists() || !root.isDirectory()) return false;
        try {
            this.root = createResourceDirectory(root, null);
            readAndCreateItems_(this.root);
        } catch (Exception e) {
            logger.error("Failed to load resource", e);
            return false;
        }
        return true;
    }

    /**
     * Given a root, this function fetches it's sub-items and maps respectively.
     * @param root
     */
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

    // Helper functions to create ResourceDirectory and ResourceFile from system Files and Folders

    private ResourceDirectory createResourceDirectory(File childDirectory, ResourceDirectory parent) {
        String name = childDirectory.getName();
        if(null == name) {
            logger.warn("Null name found for a child directory at parent: "+parent.getName());
            return null;
        }
        return new ResourceDirectory(idGenerator.generate(), childDirectory, name, childDirectory.list().length, parent);
    }

    private ResourceFile createResourceFile(File file, ResourceDirectory parent) {
        String name = FileUtilities.getFileName(file);
        if(null == name) {
            logger.warn("Null name found for a file at parent: "+parent.getName());
            return null;
        }
        String format = FileUtilities.getFileFormat(file);
        if(null == format || !ApplicationSettings.getInstance().isValidFormat(format)) return null;
        return new ResourceFile(idGenerator.generate(), file, name, format, parent);
    }
}
