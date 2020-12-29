/*
 * Copyright (c) 2020 yking-projects
 */

package com.github.zkingboos.music.player.thread;

import com.github.zkingboos.music.player.MusicPlayer;
import lombok.RequiredArgsConstructor;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;

import static javax.sound.sampled.AudioFileFormat.Type.WAVE;

@RequiredArgsConstructor
public final class MusicPlayerThread implements Runnable {

    private final MusicPlayer musicPlayer;

    @Override
    public void run() {
        try (AudioInputStream inputStream = musicPlayer.createInputStream()) {
            final String title = musicPlayer
              .getAudioPlayer()
              .getPlayingTrack()
              .getInfo()
              .title
              .replace(" ", "_");

            musicPlayer.sendMessage("Writing pcm input stream to hard disk");
            AudioSystem.write(inputStream, WAVE, new File(String.format("D:/%s.wav", title)));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
