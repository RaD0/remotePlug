package com.remotePlug.resources;

import java.io.File;

public class FileUtilities {

    public static String getFileFormat(File file) {
        return splitFile(file, 1);
    }

    public static String getFileName(File file) {
        return splitFile(file, 0);
    }

    public enum ResourceType {
        File,
        Directory
    }

    public static boolean isAFile(ResourceItem toCheck) {
        return (null != toCheck && (ResourceType.File.equals(toCheck.getType())));
    }

    public static boolean isADirectory(ResourceItem toCheck) {
        return !isAFile(toCheck);
    }

    public static ResourceFile toResourceFile(ResourceItem toConvert) {
        return (ResourceFile) toConvert;
    }

    public static ResourceDirectory toResourceDirectory(ResourceItem toConvert) {
        return (ResourceDirectory) toConvert;
    }

    private static String splitFile(File file, int filePart) {
        if(null == file || file.isDirectory()) return null;
        String fileName = file.getAbsolutePath();
        if(filePart > 1 || filePart < 0 ) return null;
        String[] split = fileName.split("\\.(?=[^\\.]+$)");
        if(split.length != 2) return null;
        return split[filePart];
    }
}
