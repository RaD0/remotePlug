package com.remotePlug.plugin.videoPlug.mediaPlayer;

import com.remotePlug.resources.ResourceFile;

import java.util.ArrayList;

public class MediaPlayerEngine {

    private static MediaPlayerEngine engineInstance = null;
    private ArrayList<MediaPlayer> runningPlayers;

    private MediaPlayerEngine() {
        runningPlayers = new ArrayList<MediaPlayer>(1);
    }

    public static boolean start() {
        if (null != engineInstance)
            return false;
        engineInstance = new MediaPlayerEngine();
        return MediaPlayerSettings.init();
    }

    public static MediaPlayerEngine getInstance() {
        return engineInstance;
    }

    public boolean operatePlayer(MediaPlayer.Option option) {
        return getPlayer().processOption(option);
    }

    public boolean operatePlayer(ResourceFile mediaItem, MediaPlayer.Option option) {
        return getPlayer().processOption(option, mediaItem);
    }

    public boolean hasNext() {
        return getPlayer().hasNext();
    }

    public boolean hasPrevious() {
        return getPlayer().hasPrevious();
    }

    public MediaPlayer.NowPlaying getNowPlaying() {
        return getPlayer().getNowPlaying();
    }

    private MediaPlayer getPlayer() {
        if (runningPlayers.isEmpty()) {
            runningPlayers.add(new MediaPlayer());
        }
        return runningPlayers.get(0);
    }
}


