package com.github.zkingboos.player.bukkit.entity;

import com.github.zkingboos.player.bukkit.struct.PlaylistInfoStruct;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface EntityMusic {

    PlaylistInfoStruct requestSong(@NonNull String url);

    CompletableFuture<PlaylistInfoStruct> requestAsynchronousSong(@NonNull String url);

    PlaylistInfoStruct requestSong(@NonNull String url, int quality);

    CompletableFuture<PlaylistInfoStruct> requestAsynchronousSong(@NonNull String url, int quality);
}
