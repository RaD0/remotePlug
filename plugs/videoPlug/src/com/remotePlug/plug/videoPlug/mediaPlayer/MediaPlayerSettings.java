package com.remotePlug.plug.videoPlug.mediaPlayer;

import com.remotePlug.settings.ApplicationSettings;
import org.apache.log4j.Logger;
import org.ini4j.Profile;

public class MediaPlayerSettings {

    final Profile.Section settings;
    static MediaPlayerSettings instance = null;
    private static Logger logger = Logger.getLogger(MediaPlayerSettings.class);

    private MediaPlayerSettings() {
        settings = ApplicationSettings.getInstance().getSettings("video");
    }

    static boolean init() {
        if (null != instance) {
            logger.error("Cannot re-initialize MediaPlayerSettings");
            return false;
        }
        instance = new MediaPlayerSettings();
        return null != instance.settings;
    }

    public static MediaPlayerSettings getInstance() {
        return instance;
    }

    public Dimension getFrameDimensions() {
        final int DEFAULT_HEIGHT = 1200, DEFAULT_WIDTH = 600;
        String dimension = get("defaultScreenSize");
        if (null == dimension || dimension.isEmpty() || !dimension.matches("\\d+x\\d+"))
            return new Dimension(DEFAULT_HEIGHT, DEFAULT_WIDTH);
        String[] dimensions = dimension.split("x");
        int height = toInt(dimensions[0]), width = toInt(dimensions[1]);
        return new Dimension(
                (0 <= height) ? DEFAULT_HEIGHT : height,
                (0 <= width) ? DEFAULT_WIDTH : width
        );
    }

    private String get(String key) {
        return (null == key) ? null : settings.get(key);
    }

    private int toInt(String integer) {
        try {
            return Integer.parseInt(integer);
        } catch (Exception e) {
            return 0;
        }
    }

    public class Dimension {
        private final int height, width;

        Dimension(int height, int width) {
            this.height = height;   this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        @Override
        public String toString() {
            return height + "x" + width;
        }

        public java.awt.Dimension toAWTDimension() {
            return new java.awt.Dimension(height, width);
        }
    }

}
