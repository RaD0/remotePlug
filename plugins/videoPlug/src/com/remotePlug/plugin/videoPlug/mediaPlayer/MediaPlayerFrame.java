package com.remotePlug.plugin.videoPlug.mediaPlayer;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;

public class MediaPlayerFrame extends JFrame {

    MediaPlayerFrame(EmbeddedMediaPlayerComponent mediaPlayerComponent) {
        super("");
        setContentPane(mediaPlayerComponent);
        setUpDefaults();
    }

    private void setUpDefaults() {
        setLocation(100, 100);
        setSize(MediaPlayerSettings.getInstance().getFrameDimensions().toAWTDimension());
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        setVisible(true);
    }

}


