package com.remotePlug;

import com.remotePlug.components.CoreComponentsLauncher;
import org.apache.log4j.PropertyConfigurator;

public class Launcher {

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Invalid number of arguments passed. Required 1. Passed "+args.length);
            return;
        }
        // Set up the log file
        PropertyConfigurator.configure(args[0]+"/logs.properties");

        // Load all components
        if(!CoreComponentsLauncher.getInstance().launch(args[0]))
            return;

    }
}
