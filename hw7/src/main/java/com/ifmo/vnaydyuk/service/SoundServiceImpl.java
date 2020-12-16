package com.ifmo.vnaydyuk.service;

import javax.media.bean.playerbean.MediaPlayer;
import java.net.URL;

public class SoundServiceImpl implements SoundService {

    @Override
    public void playSound(String path) throws InterruptedException {
        System.out.println(path);
        URL url = getClass().getClassLoader().getResource(path);
        System.out.println(url);
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setPlaybackLoop(false);
        assert url != null;
        mediaPlayer.setMediaLocation(url.toString());
        mediaPlayer.prefetch();
        mediaPlayer.start();
        Thread.sleep(5000);
        mediaPlayer.close();
    }
}
