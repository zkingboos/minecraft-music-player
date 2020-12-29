/*
 * Copyright (c) 2020 yking-projects
 */

package com.github.zkingboos.music.player.listener;

import com.github.zkingboos.music.player.MusicPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class GlobalMusicListener extends AudioEventAdapter implements AudioLoadResultHandler {

    private final MusicPlayer musicPlayer;

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        musicPlayer.cleanupThreadJob();
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        musicPlayer.addSingleTrackToQueue(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        musicPlayer.addSingleTrackToQueue(playlist.getTracks().get(0));
//        musicPlayer.addTracksToQueue(playlist);
    }

    @Override
    public void noMatches() {
        musicPlayer.sendMessage("No matches found");
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        musicPlayer.sendMessage("Cant load playlist or music... " + exception.getLocalizedMessage());
        exception.printStackTrace();
    }
}
