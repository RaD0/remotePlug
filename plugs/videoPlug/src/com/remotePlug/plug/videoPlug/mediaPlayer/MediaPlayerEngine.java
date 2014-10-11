package com.remotePlug.plug.videoPlug.mediaPlayer;

import com.remotePlug.resources.ResourceFile;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Makes sure that there are only a limited number of MediaPlayers running. Ideally, one for Video and one for Audio
 *
 * Any communication with the mediaPlayer should be done through this.
 */
public class MediaPlayerEngine {

    private static MediaPlayerEngine engineInstance = null;
    private ArrayList<com.remotePlug.plug.videoPlug.mediaPlayer.MediaPlayer> runningPlayers;
    private static Logger logger = Logger.getLogger(MediaPlayerEngine.class);

    private MediaPlayerEngine() {
        runningPlayers = new ArrayList<com.remotePlug.plug.videoPlug.mediaPlayer.MediaPlayer>(1);
    }

    public static boolean start() {
        if (null != engineInstance) {
            logger.error("Cannot re-initialize MediaPlayerEngine");
            return false;
        }
        engineInstance = new MediaPlayerEngine();
        return MediaPlayerSettings.init();
    }

    public static MediaPlayerEngine getInstance() {
        return engineInstance;
    }

    public boolean operatePlayer(com.remotePlug.plug.videoPlug.mediaPlayer.MediaPlayer.Option option) {
        return getPlayer().processOption(option);
    }

    public boolean operatePlayer(ResourceFile mediaItem, com.remotePlug.plug.videoPlug.mediaPlayer.MediaPlayer.Option option) {
        return getPlayer().processOption(option, mediaItem);
    }

    public boolean hasNext() {
        return getPlayer().hasNext();
    }

    public boolean hasPrevious() {
        return getPlayer().hasPrevious();
    }

    public com.remotePlug.plug.videoPlug.mediaPlayer.MediaPlayer.NowPlaying getNowPlaying() {
        return getPlayer().getNowPlaying();
    }

    private com.remotePlug.plug.videoPlug.mediaPlayer.MediaPlayer getPlayer() {
        if (runningPlayers.isEmpty()) {
            runningPlayers.add(new com.remotePlug.plug.videoPlug.mediaPlayer.MediaPlayer());
        }
        return runningPlayers.get(0);
    }
}


