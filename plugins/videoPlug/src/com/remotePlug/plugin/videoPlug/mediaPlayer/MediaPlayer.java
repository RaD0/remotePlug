package com.remotePlug.plugin.videoPlug.mediaPlayer;

import com.remotePlug.resources.ResourceMediaItem;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.util.HashMap;

public class MediaPlayer {

    private final EmbeddedMediaPlayer mediaPlayer;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private MediaPlayerFrame frame = null;
    private ResourceMediaItem nowPlaying = null;
    private HashMap<Option,MediaPlayerOption> availableOptions;

    MediaPlayer() {
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        mediaPlayer = mediaPlayerComponent.getMediaPlayer();
        availableOptions = new HashMap<Option, MediaPlayerOption>();
        availableOptions.put(Option.Play, new Play());
        availableOptions.put(Option.Pause, new Pause());
        availableOptions.put(Option.Stop, new Stop());
        availableOptions.put(Option.UnPause, new Unpause());
    }

    public boolean processOption(Option toProcess) {
        return processOption(toProcess, nowPlaying);
    }

    public boolean processOption(Option toProcess, ResourceMediaItem mediaItem) {
        return (null != toProcess
                && null != mediaItem
                && processOption_(toProcess, mediaItem));
    }

    public enum Option {
        Play, Pause, UnPause, Stop, VolumeUp, VolumeDown, Next, Previous
    }

    private boolean processOption_(Option toProcess, ResourceMediaItem mediaItem) {
        return (availableOptions.containsKey(toProcess)
                && null != availableOptions.get(toProcess)
                && availableOptions.get(toProcess).execute(mediaItem));
    }

    private class Play implements MediaPlayerOption {
        @Override
        public boolean execute(ResourceMediaItem mediaItem) {
            frame = new MediaPlayerFrame(mediaPlayerComponent);
            if (mediaPlayer.playMedia(mediaItem.getPath())) {
                nowPlaying = mediaItem;
                return true;
            }
            return false;
        }
    }

    private class Pause implements MediaPlayerOption {
        @Override
        public boolean execute(ResourceMediaItem mediaItem) {
            if (null == frame || null == nowPlaying) return false;
            if (mediaPlayer.canPause()) mediaPlayer.pause();
            return true;
        }
    }

    private class Stop implements MediaPlayerOption {
        @Override
        public boolean execute(ResourceMediaItem mediaItem) {
            if (null == frame || null == nowPlaying) return false;
            mediaPlayer.stop();
            nowPlaying = null;
            return true;
        }
    }

    private class Unpause implements MediaPlayerOption {
        @Override
        public boolean execute(ResourceMediaItem mediaItem) {
            if (null == frame || null == nowPlaying)  return false;
            if (mediaPlayer.isPlayable())
                mediaPlayer.play();
            return true;
        }
    }











}
