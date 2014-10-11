package com.remotePlug.plug.uiPlug;

import com.remotePlug.components.Plug;
import com.remotePlug.plug.uiPlug.spark.SparkEngine;

public class UIPlug implements Plug {

    @Override
    public boolean load() {
        return SparkEngine.ignite();
    }

    @Override
    public boolean unload() {
        return true;
    }
}
