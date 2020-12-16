package com.ifmo.vnaydyuk;

import javax.media.bean.playerbean.MediaPlayer;
import java.net.URL;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        URL resource = Test.class.getClassLoader().getResource("horse.wav");
        MediaPlayer player = new MediaPlayer();
        player.setMediaLocation("file:///" + resource.getPath());
        player.setPlaybackLoop(false);
        player.start();
        Thread.sleep(5000);
        player.close();
    }
}
