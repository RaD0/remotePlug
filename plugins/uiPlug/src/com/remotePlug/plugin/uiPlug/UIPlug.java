package com.remotePlug.plugin.uiPlug;

import com.remotePlug.components.Plugin;
import com.remotePlug.plugin.uiPlug.spark.SparkEngine;
import com.remotePlug.plugin.uiPlug.spark.UISettings;

public class UIPlug implements Plugin {

    @Override
    public boolean load() {
        return SparkEngine.ignite();
    }

    @Override
    public boolean unload() {
        return true;
    }
}
