package com.remotePlug.plugin.videoPlug;

import com.remotePlug.handlers.Handler;
import com.remotePlug.handlers.PlugRequest;
import com.remotePlug.plugin.videoPlug.mediaPlayer.MediaPlayer;
import com.remotePlug.plugin.videoPlug.mediaPlayer.MediaPlayerEngine;
import com.remotePlug.resources.FileUtilities;
import com.remotePlug.resources.ResourceFile;

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

    private MediaPlayer.Option convertToOption(String option) {
        if (null == option) return null;
        for(MediaPlayer.Option option_ : MediaPlayer.Option.values()) {
            if (option_.toString().equals(option))
                return option_;
        }
        return null;
    }
}
