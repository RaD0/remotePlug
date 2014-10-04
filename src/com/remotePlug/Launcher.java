package com.remotePlug;

import com.remotePlug.components.CoreComponentsLauncher;
import com.remotePlug.handlers.PlugRequest;
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

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String command = br.readLine();
                if ("bye".equals(command)) break;
                if ("list".equals(command)) {
                    for(ResourceMediaItem item: ResourceManager.getInstance().getItems()) {
                        print(item.toString());
                    }
                    continue;
                }
                String[] split = command.split(" ");
                // Send ONLY these commands: Play <filename>, Pause <filename>, Stop <filename>
                ResourceMediaItem mediaItem = ResourceManager.getInstance().getItem(split[1]);
                if (null == mediaItem) continue;
                PlugRequest request = new PlugRequest(mediaItem, split[0]);
                RequestHandler.getInstance().handleMediaRequest(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void print(String toPrint) {
        System.out.println(toPrint);
    }


}
