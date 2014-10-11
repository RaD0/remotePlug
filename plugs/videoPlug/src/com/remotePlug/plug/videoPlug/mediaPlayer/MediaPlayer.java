package com.remotePlug.plug.videoPlug.mediaPlayer;

import com.remotePlug.resources.ResourceFile;
import org.apache.log4j.Logger;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A MediaPlayer is indeed a MediaPlayer in real sense.
 * It controls currently being handled by this MediaPlayer are in <code> enum Option </code>
 *
 * Each Control is of type <code> ControlOption </code>
 * All the list of controls are listed in <code> HashMap<Option,ControlOption> availableOptions </code>
 *
 * When a ControlOption is requested, the MediaPlayer loops through the availableOptions and executes the matched controller
 */
public class MediaPlayer {

    private final EmbeddedMediaPlayer mediaPlayer;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private MediaPlayerFrame frame = null;
    private NowPlaying nowPlaying = new NowPlaying(Status.Closed, null);
    private HashMap<Option,ControlOption> availableOptions;
    private ArrayList<ResourceFile> playList = new ArrayList<>();
    private int playing = -1;
    private final int VOLUME_INTERVAL = 20;
    private Logger logger = Logger.getLogger(MediaPlayer.class);

    MediaPlayer() {
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        mediaPlayer = mediaPlayerComponent.getMediaPlayer();
        mediaPlayer.setVolume(50);
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

    boolean processOption(Option toProcess) {
        if (null == nowPlaying) return false;
        return processOption(toProcess, nowPlaying.getMediaItem());
    }

    boolean processOption(Option toProcess, ResourceFile mediaItem) {
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

    NowPlaying getNowPlaying() {
        return nowPlaying;
    }

    boolean hasNext() {
        return playList.size() > 0;
    }

    boolean hasPrevious() {
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

    // Controller Options

    private class Play implements ControlOption {

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

    private class Pause implements ControlOption {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null == frame || null == nowPlaying) return false;
            if (mediaPlayer.canPause()) mediaPlayer.pause();
            nowPlaying.updateStatus(Status.Paused);
            return true;
        }
    }

    private class Stop implements ControlOption {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null == frame || null == nowPlaying) return false;
            mediaPlayer.stop();
            nowPlaying.update(Status.Closed, null);
            return true;
        }
    }

    private class Unpause implements ControlOption {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null == frame || null == nowPlaying)  return false;
            if (mediaPlayer.isPlayable())
                mediaPlayer.play();
            nowPlaying.updateStatus(Status.Playing);
            return true;
        }
    }

    private class AddToPlaylist implements ControlOption {

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

    private class Next implements ControlOption {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null == frame || null == nowPlaying || null == mediaItem)  return false;
            if (!playList.isEmpty() && playList.size() > (playing+1)) {
                return processOption_(Option.Play, playList.get(++playing));
            } else return false;
        }
    }

    private class Previous implements ControlOption {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null == frame || null == nowPlaying || null == mediaItem)  return false;
            if (!playList.isEmpty() && playing > 0)
                return processOption_(Option.Play, playList.get(--playing));
            return false;
        }
    }

    private class VolumeUp implements ControlOption {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            int threshold = 100 - VOLUME_INTERVAL;
            if (null != nowPlaying.getMediaItem() && mediaPlayer.getVolume() <= threshold) {
                    mediaPlayer.setVolume(mediaPlayer.getVolume()+VOLUME_INTERVAL);
            }
            return true;
        }
    }

    private class VolumeDown implements ControlOption {

        @Override
        public boolean execute(ResourceFile mediaItem) {
            if (null != nowPlaying.getMediaItem()) {
                if (mediaPlayer.getVolume() >= VOLUME_INTERVAL) {
                  mediaPlayer.setVolume(mediaPlayer.getVolume()-VOLUME_INTERVAL);
                }
            }
            return true;
        }
    }

}
