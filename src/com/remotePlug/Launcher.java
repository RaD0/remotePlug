package com.remotePlug;

import com.remotePlug.components.CoreComponentsLauncher;

public class Launcher {

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Invalid number of arguments passed. Required 1. Passed "+args.length);
            return;
        }
        if(!CoreComponentsLauncher.getInstance().launch(args[0]))
            return;
    }
}
