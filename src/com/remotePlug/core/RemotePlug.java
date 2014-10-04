package com.remotePlug.core;

public class RemotePlug {

    private static RemotePlug instance = null;

    private RemotePlug() {}

    public static RemotePlug getInstance() {
        if(null == instance)
            instance = new RemotePlug();
        return instance;
    }

    public void processRequest(String mediaFilePath) {}

}
