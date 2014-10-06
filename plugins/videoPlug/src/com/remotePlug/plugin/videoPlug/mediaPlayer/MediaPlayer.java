package com.remotePlug.plugin.videoPlug.mediaPlayer;

import com.remotePlug.resources.ResourceFile;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;

import java.util.*;

public class MediaPlayer {

    private final EmbeddedMediaPlayer mediaPlayer;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private MediaPlayerFrame frame = null;
    private NowPlaying nowPlaying = new NowPlaying(Status.Closed, null);
    private HashMap<Option,MediaPlayerOption> availableOptions;
    private ArrayList<ResourceFile> playList = new ArrayList<>();
    private int playing = -1;
    private final int VOLUME_INTERVAL = 20;

    MediaPlayer() {
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        mediaPlayer = mediaPlayerComponent.getMediaPlayer();
        availableOptions = new HashMap<>();
        availableOptions.put(Option.Play, new Play());
        availableOptions.put(Option.Pause, new Pause());
        availableOptions.put(Option.Stop, new Stop());
        availableOptions.put(Option.UnPause, new Unpause());
        availableOptions.put(Option.AddToPlaylist, new AddToPlaylist());
        availableOptions.put(Option.Next, new Next());
        availableOptions.put(Option.Previous, new Previous());
        availableOptions.put(Option.VolumeUp, new VolumeUp());
        availableOptions.put(Option.VolumeDown, new VolumeDown());
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
        Play, Pause, UnPause, Stop, VolumeUp, VolumeDown, Next, Previous, AddToPlaylist
    }

    public enum Status {
        Playing, Paused, Stopped, Closed
    }

    public NowPlaying getNowPlaying() {
        return nowPlaying;
    }

    public boolean hasNext() {
        return playList.size() > 0;
    }

    public boolean hasPrevious() {
        return (playing > 0);
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

    private void onCloseFrame() {
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (null != nowPlaying.getMediaItem())
                    processOption_(Option.Stop, nowPlaying.getMediaItem());
                frame.dispose();
            }
        });
    }

    private void createFrame() {
        frame = new MediaPlayerFrame(mediaPlayerComponent);
        onCloseFrame();
    }

    private class Play implements MediaPlayerOption {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null != nowPlaying.getMediaItem()) {
                processOption_(Option.Stop, nowPlaying.getMediaItem());
                frame.dispose();
                frame = null;
            }
            createFrame();
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

    private class AddToPlaylist implements MediaPlayerOption {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null == frame || null == nowPlaying || null == mediaItem)  return false;
            if (null != nowPlaying.getMediaItem() && playList.isEmpty()) {
                playList.add(nowPlaying.getMediaItem());
            }
            if (playing == -1) playing++;
            return playList.add(mediaItem);
        }
    }

    private class Next implements MediaPlayerOption {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null == frame || null == nowPlaying || null == mediaItem)  return false;
            if (!playList.isEmpty() && playList.size() > (playing+1)) {
                return processOption_(Option.Play, playList.get(++playing));
            } else return false;
        }
    }

    private class Previous implements MediaPlayerOption  {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null == frame || null == nowPlaying || null == mediaItem)  return false;
            if (!playList.isEmpty() && playing > 0)
                return processOption_(Option.Play, playList.get(--playing));
            return false;
        }
    }

    private class VolumeUp implements MediaPlayerOption {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            int threshold = 100 - VOLUME_INTERVAL;
            if (null != nowPlaying.getMediaItem() && mediaPlayer.getVolume() <= threshold) {
                    mediaPlayer.setVolume(mediaPlayer.getVolume()+VOLUME_INTERVAL);
            }
            return true;
        }
    }

    private class VolumeDown implements MediaPlayerOption {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null != nowPlaying.getMediaItem()) {
                if (mediaPlayer.getVolume() >= VOLUME_INTERVAL) {
                  mediaPlayer.setVolume(mediaPlayer.getVolume()-VOLUME_INTERVAL);
                } else mediaPlayer.setVolume(0);
            }
            return true;
        }
    }

}
