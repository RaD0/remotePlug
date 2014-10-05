package com.remotePlug.plugin.videoPlug;

import com.remotePlug.handlers.Handler;
import com.remotePlug.handlers.PlugRequest;
import com.remotePlug.plugin.videoPlug.mediaPlayer.MediaPlayer;
import com.remotePlug.plugin.videoPlug.mediaPlayer.MediaPlayerEngine;
import com.remotePlug.resources.FileUtilities;
import com.remotePlug.resources.ResourceFile;
import com.remotePlug.resources.ResourceItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class VideoHandler implements Handler {

    final String acceptableFormat = "mp4";

    @Override
    public boolean canHandle(PlugRequest request) {
        return (null != request
                && null != request.getResourceItem()
                && FileUtilities.isAFile(request.getResourceItem())
                && acceptableFormat.equals(FileUtilities.toResourceFile(request.getResourceItem()).getFormat()));
    }

    @Override
    public void handle(PlugRequest request) {
        if(null != request && null != request.getResourceItem()) {
            ResourceFile media = FileUtilities.toResourceFile(request.getResourceItem());
            MediaPlayer.Option requestType = convertToOption((String) request.getRequestType());
            if(null == requestType) return;
            MediaPlayerEngine.getInstance().operatePlayer(media, requestType);
            System.out.println("VideoPlug Handler handling: "+media.getName());
        }
    }

    @Override
    public Collection<String> getPermittedOperations(ResourceItem item) {
        if (null == item) Collections.emptyList();
        ArrayList<String> permittedOperations = new ArrayList<>();
        for(MediaPlayer.Option option: MediaPlayer.Option.values())
            permittedOperations.add(option.toString());
        MediaPlayer.NowPlaying nowPlaying = MediaPlayerEngine.getInstance().getNowPlaying();

        if (null == nowPlaying || MediaPlayer.Status.Closed.equals(nowPlaying.getStatus()) || null == nowPlaying.getMediaItem()) {
            shrinkToMinimalOptions(permittedOperations);
            return Collections.unmodifiableCollection(permittedOperations);
        }
        if (item.equals(nowPlaying.getMediaItem())) {
            if (MediaPlayer.Status.Paused.equals(nowPlaying.getStatus())) {
                permittedOperations.remove(MediaPlayer.Option.Pause.toString());
                permittedOperations.remove(MediaPlayer.Option.Play.toString());
            } else if (MediaPlayer.Status.Playing.equals(nowPlaying.getStatus())) {
                permittedOperations.remove(MediaPlayer.Option.Play.toString());
                permittedOperations.remove(MediaPlayer.Option.UnPause.toString());
            }
        } else {
            shrinkToMinimalOptions(permittedOperations);
        }
        return Collections.unmodifiableCollection(permittedOperations);
    }

    private void shrinkToMinimalOptions(ArrayList<String> permittedOperations) {
        permittedOperations.remove(MediaPlayer.Option.Pause.toString());
        permittedOperations.remove(MediaPlayer.Option.Stop.toString());
        permittedOperations.remove(MediaPlayer.Option.UnPause.toString());
        permittedOperations.remove(MediaPlayer.Option.VolumeUp.toString());
        permittedOperations.remove(MediaPlayer.Option.VolumeDown.toString());
        permittedOperations.remove(MediaPlayer.Option.Next.toString());
        permittedOperations.remove(MediaPlayer.Option.Previous.toString());
    }

    private MediaPlayer.Option convertToOption(String option) {
        if (null == option) return null;
        for(MediaPlayer.Option option_ : MediaPlayer.Option.values()) {
            if (option_.toString().equals(option))
                return option_;
        }
        return null;
    }
}
