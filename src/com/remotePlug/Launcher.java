package com.remotePlug;

import com.remotePlug.components.CoreComponentsLauncher;
import com.remotePlug.handlers.RequestHandler;
import com.remotePlug.resources.ResourceManager;
import com.remotePlug.resources.ResourceMediaItem;

import java.io.*;

public class Launcher {

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Invalid number of arguments passed. Required 1. Passed "+args.length);
            return;
        }
        if(!CoreComponentsLauncher.getInstance().launch(args[0]))
            return;
        runSimpleTest();
    }

    private static void runSimpleTest() {
        try {
            File testData = new File("test/test_items.txt");
            BufferedReader br = new BufferedReader(new FileReader(testData));
            String line = null;
            while(null != (line = br.readLine())) {
                ResourceMediaItem mediaItem = ResourceManager.getInstance().getItem(line);
                if(!RequestHandler.getInstance().handleMediaRequest(mediaItem)) {
                    print("No handler found for: "+mediaItem.getFormat());
                }
            }
        } catch (Exception e) {

        }
    }

    private static void print(String toPrint) {
        System.out.println(toPrint);
    }


}
