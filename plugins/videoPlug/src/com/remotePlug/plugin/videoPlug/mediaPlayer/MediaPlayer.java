package com.remotePlug.plugin.videoPlug.mediaPlayer;

import com.remotePlug.resources.ResourceFile;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.util.HashMap;

public class MediaPlayer {

    private final EmbeddedMediaPlayer mediaPlayer;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private MediaPlayerFrame frame = null;
    private NowPlaying nowPlaying = new NowPlaying(Status.Closed, null);
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
        if (null == nowPlaying) return false;
        return processOption(toProcess, nowPlaying.getMediaItem());
    }

    public boolean processOption(Option toProcess, ResourceFile mediaItem) {
        return (null != toProcess
                && null != mediaItem
                && processOption_(toProcess, mediaItem));
    }

    public enum Option {
        Play, Pause, UnPause, Stop, VolumeUp, VolumeDown, Next, Previous
    }

    public enum Status {
        Playing, Paused, Stopped, Closed
    }

    public NowPlaying getNowPlaying() {
        return nowPlaying;
    }

    public class NowPlaying {
        private Status status;
        private ResourceFile playing;

        NowPlaying(Status status, ResourceFile playing) {
            this.status = status;   this.playing = playing;
        }

        void updateStatus(Status newStatus) {
            status = newStatus;
        }

        void updatePlaying(ResourceFile nowPlaying) {
            playing = nowPlaying;
        }

        void update(Status newStatus, ResourceFile nowPlaying) {
            updateStatus(newStatus);    updatePlaying(nowPlaying);
        }

        public ResourceFile getMediaItem() {
            return playing;
        }

        public Status getStatus() {
            return status;
        }
    }

    private boolean processOption_(Option toProcess, ResourceFile mediaItem) {
        return (availableOptions.containsKey(toProcess)
                && null != availableOptions.get(toProcess)
                && availableOptions.get(toProcess).execute(mediaItem));
    }

    private class Play implements MediaPlayerOption {
        @Override
        public boolean execute(ResourceFile mediaItem) {
            frame = new MediaPlayerFrame(mediaPlayerComponent);
            if (mediaPlayer.playMedia(mediaItem.getPath())) {
                nowPlaying.update(Status.Playing, mediaItem);
                return true;
            }
            return false;
        }
    }

    private class Pause implements MediaPlayerOption {
        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null == frame || null == nowPlaying) return false;
            if (mediaPlayer.canPause()) mediaPlayer.pause();
            nowPlaying.updateStatus(Status.Paused);
            return true;
        }
    }

    private class Stop implements MediaPlayerOption {
        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null == frame || null == nowPlaying) return false;
            mediaPlayer.stop();
            nowPlaying.update(Status.Closed, null);
            return true;
        }
    }

    private class Unpause implements MediaPlayerOption {
        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null == frame || null == nowPlaying)  return false;
            if (mediaPlayer.isPlayable())
                mediaPlayer.play();
            nowPlaying.updateStatus(Status.Playing);
            return true;
        }
    }

}
