package com.remotePlug.plugin.videoPlug;

import com.remotePlug.handlers.Handler;
import com.remotePlug.handlers.HandlingData;
import com.remotePlug.handlers.PlugRequest;
import com.remotePlug.plugin.videoPlug.mediaPlayer.MediaPlayer;
import com.remotePlug.plugin.videoPlug.mediaPlayer.MediaPlayerEngine;
import com.remotePlug.resources.FileUtilities;
import com.remotePlug.resources.ResourceFile;
import com.remotePlug.resources.ResourceItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;


public class VideoHandler implements Handler {

    private Set<String> acceptableFormats;

    VideoHandler(Set<String> formats) {
        acceptableFormats = formats;
    }

    @Override
    public boolean canHandle(PlugRequest request) {
        return (null != request
                && null != request.getResourceItem()
                && FileUtilities.isAFile(request.getResourceItem())
                && acceptableFormats.contains(FileUtilities.toResourceFile(request.getResourceItem()).getFormat()));
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
    public void packHandlingData(ResourceItem item, HandlingData handlingData) {
        if (null == item || null == handlingData) return;
        ArrayList<String> permittedOperations = new ArrayList<>();
        for(MediaPlayer.Option option: MediaPlayer.Option.values())
            permittedOperations.add(option.toString());
        MediaPlayer.NowPlaying nowPlaying = MediaPlayerEngine.getInstance().getNowPlaying();
        boolean isActive = false;
        String startOperation = "";
        if (null == nowPlaying || MediaPlayer.Status.Closed.equals(nowPlaying.getStatus()) || null == nowPlaying.getMediaItem()) {
            shrinkToMinimalOptions(permittedOperations);
            startOperation = MediaPlayer.Option.Play.toString();
        } else {
            if (item.equals(nowPlaying.getMediaItem())) {
                isActive = true;
                if (MediaPlayer.Status.Paused.equals(nowPlaying.getStatus())) {
                    permittedOperations.remove(MediaPlayer.Option.Pause.toString());
                    permittedOperations.remove(MediaPlayer.Option.Play.toString());
                    startOperation = MediaPlayer.Option.UnPause.toString();
                } else if (MediaPlayer.Status.Playing.equals(nowPlaying.getStatus())) {
                    permittedOperations.remove(MediaPlayer.Option.Play.toString());
                    permittedOperations.remove(MediaPlayer.Option.UnPause.toString());
                    startOperation = MediaPlayer.Option.Pause.toString();
                }
            } else {
                shrinkToMinimalOptions(permittedOperations);
            }
        }
        handlingData.update(startOperation, permittedOperations, isActive);
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
