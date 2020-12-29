/*
 * Copyright (c) 2020 yking-projects
 */

package com.github.zkingboos.music.player;

import com.github.zkingboos.music.player.listener.GlobalMusicListener;
import com.github.zkingboos.music.player.thread.MusicPlayerThread;
import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.format.AudioPlayerInputStream;
import com.sedmelluq.discord.lavaplayer.player.AudioConfiguration;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import javax.sound.sampled.AudioInputStream;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import static com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats.DISCORD_PCM_S16_BE;

@Getter
public final class MusicPlayer {

    private final static ForkJoinPool FORK_JOIN_POOL;
    private final static AudioPlayerManager AUDIO_PLAYER_MANAGER;
    private final static AudioDataFormat AUDIO_DATA_FORMAT;

    static {
        FORK_JOIN_POOL = new ForkJoinPool(2);
        AUDIO_PLAYER_MANAGER = new DefaultAudioPlayerManager();
        final AudioConfiguration configuration = AUDIO_PLAYER_MANAGER.getConfiguration();
        configuration.setOutputFormat(AUDIO_DATA_FORMAT = DISCORD_PCM_S16_BE);
        AudioSourceManagers.registerRemoteSources(AUDIO_PLAYER_MANAGER);
    }

    private final GlobalMusicListener globalMusicListener;
    private final MusicPlayerThread playerThread;
    private final AudioPlayer audioPlayer;
    private final MusicPlayerQueue queue;
    private final Player player;
    private AudioInputStream inputStream;
    private AudioPlaylist playlist;
    private ForkJoinTask<?> task;
    public MusicPlayer(@NonNull Player player) {
        this.globalMusicListener = new GlobalMusicListener(this);
        this.playerThread = new MusicPlayerThread(this);
        this.audioPlayer = AUDIO_PLAYER_MANAGER.createPlayer();
        this.queue = new MusicPlayerQueue();
        this.player = player;

        audioPlayer.addListener(globalMusicListener);
    }

    public static void liveItUp() {
        //TODO: Just call to call the class to load static fields
    }

    public static void fuckItUp(@NonNull MusicPlayerRegistry registry) {
        AUDIO_PLAYER_MANAGER.shutdown();
        FORK_JOIN_POOL.shutdownNow();
        registry.values().forEach(MusicPlayer::end);
    }

    public void addSingleTrackToQueue(@NonNull AudioTrack audioTrack) {
//        this.playlist = null;
//        queue.add(audioTrack);

        audioPlayer.playTrack(audioTrack);
        player.sendMessage("Started to play audio track");
        startThreadJob();
    }

    public void addTracksToQueue(@NonNull AudioPlaylist playlist) {
        this.playlist = playlist;
        queue.addAll(playlist.getTracks());
    }

    public void sendMessage(String... message) {
        player.sendMessage(message);
    }

    public void searchFor(@NonNull String url) {
        AUDIO_PLAYER_MANAGER.loadItem(url, globalMusicListener);
    }

    public void startThreadJob() {
        player.sendMessage("Started pcm encoding job");
        this.task = FORK_JOIN_POOL.submit(playerThread);
    }

    @SneakyThrows
    public void cleanupThreadJob() {
        player.sendMessage("Finished writing");
        task.cancel(true);

        if (inputStream != null) inputStream.close();
        audioPlayer.stopTrack();
    }

    public void end() {
        audioPlayer.destroy();
    }

    public AudioInputStream createInputStream() {
        return (inputStream = AudioPlayerInputStream.createStream(
          audioPlayer,
          AUDIO_DATA_FORMAT,
          10000L,
          false
        ));
    }
}
