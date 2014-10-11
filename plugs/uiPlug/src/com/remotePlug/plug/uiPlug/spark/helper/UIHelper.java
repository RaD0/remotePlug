package com.remotePlug.plug.uiPlug.spark.helper;

import com.remotePlug.resources.FileUtilities;
import com.remotePlug.resources.ResourceItem;

import java.util.ArrayList;

/**
 * Just another neighborhood friendly helper :)
 */
public class UIHelper {

    private static UIHelper instance = null;
    private ArrayList<MiniMap> iconMaps = new ArrayList<>();

    private UIHelper() {
        iconMaps.add(new MiniMap("icon-play-circle", "mp4", "avi"));
        iconMaps.add(new MiniMap("icon-music", "mp3"));
        iconMaps.add(new MiniMap("icon-file", "txt", "pdf", "doc"));
        iconMaps.add(new MiniMap("icon-folder-open", "dir"));
        iconMaps.add(new MiniMap("icon-play-circle", "play", "unpause"));
        iconMaps.add(new MiniMap("icon-pause", "pause"));
        iconMaps.add(new MiniMap("icon-stop", "stop"));
        iconMaps.add(new MiniMap("icon-step-forward", "next"));
        iconMaps.add(new MiniMap("icon-step-backward", "previous"));
        iconMaps.add(new MiniMap("icon-volume-up", "volumeup"));
        iconMaps.add(new MiniMap("icon-volume-down", "volumedown"));
    }

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

    public String getIcon(String format) {
        return getIcon_(format);
    }

    public String getIcon(String format, int times) {
        if (times <=0) times = 3;
        return getIcon_(format)+" icon-"+times+"x";
    }

    private String getIcon_(String format) {
        if (null == format) return "";
        format = format.toLowerCase();
        for(MiniMap iconMap: iconMaps) {
            if (iconMap.match(format)) return iconMap.getValue();
        }
        return "";
    }

}
