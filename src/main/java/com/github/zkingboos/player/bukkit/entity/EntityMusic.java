package com.github.zkingboos.player.bukkit.entity;

import com.sapher.youtubedl.DownloadProgressCallback;
import com.sapher.youtubedl.mapper.VideoInfo;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;

public interface EntityMusic {

    VideoInfoStruct requestSong(@NonNull String url);

    CompletableFuture<VideoInfoStruct> requestAsynchronousSong(@NonNull String url);

    VideoInfoStruct requestSong(@NonNull String url, int quality);

    CompletableFuture<VideoInfoStruct> requestAsynchronousSong(@NonNull String url, int quality);
}
