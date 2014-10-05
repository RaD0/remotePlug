package com.remotePlug.plugin.uiPlug.spark.helper;

import com.remotePlug.resources.FileUtilities;
import com.remotePlug.resources.ResourceItem;

public class UIHelper {

    private static UIHelper instance = null;

    private UIHelper() {}

    public static UIHelper getInstance() {
        if (null == instance)
            instance = new UIHelper();
        return instance;
    }

    public boolean isAFile(ResourceItem item) {
        return FileUtilities.isAFile(item);
    }

    public boolean isADir(ResourceItem item) {
        return FileUtilities.isADirectory(item);
    }

}
