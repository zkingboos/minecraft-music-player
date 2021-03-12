package com.github.zkingboos.player.bukkit.entity;

import com.github.zkingboos.player.bukkit.manager.EntityMusicManager;
import com.github.zkingboos.player.bukkit.struct.PlaylistInfoStruct;
import com.github.zkingboos.player.bukkit.struct.VideoInfoStruct;
import com.github.zkingboos.player.resourcepack.ResourcePack;
import com.github.zkingboos.youtubedl.YoutubeDL;
import com.github.zkingboos.youtubedl.entry.playlist.PlaylistInfo;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Data
public class BukkitEntityMusic implements EntityMusic {

    private final static int DEFAULT_STACK_QUALITY = 250;
    private final static String DEFAULT_FILE_EXTENSION = ".ogg";

    //private final Queue<VideoInfoStruct> queue;
    private final List<VideoInfoStruct> queue;
    private final File folder;
    private final Player player;

    @SneakyThrows
    public BukkitEntityMusic(@NonNull File pluginFolder, @NonNull Player player) {
        //this.queue = new ConcurrentLinkedQueue<>();
        this.queue = new LinkedList<>();
        this.folder = new File(pluginFolder, player.getUniqueId().toString());
        this.player = player;
        if (!folder.exists()) folder.mkdirs();
    }

    public ResourcePack writeResourcePack() {
        final ResourcePack resourcePack = new ResourcePack(new File(folder, "resourcepack.zip"))
          .writePackMeta(6, "meucu")
          .writeAllFiles("assets/minecraft/sounds", queue
            .parallelStream()
            .map(VideoInfoStruct::getArtifact)
            .collect(Collectors.toList()))
          .writeSounds(queue
            .parallelStream()
            .map(VideoInfoStruct::getArtifact)
            .map(File::getName)
            .map(String::toLowerCase)
            .collect(Collectors.toSet()));
        try {
            resourcePack.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return resourcePack;
    }

    @Override
    public PlaylistInfoStruct requestSong(@NonNull String url, int quality) {
        try {
            if (!url.startsWith("https://")) url = "ytsearch1:".concat(url);
            final PlaylistInfo playlistInfo = YoutubeDL
              .from(url, folder.getAbsolutePath())
              .id()
              .audioQuality(quality)
              .extractAudio()
              .audioFormat("vorbis")
              .format("bestaudio")
              .noCacheDir()
              .maxDownload(10)
              .get();

            final PlaylistInfoStruct playlistInfoStruct = new PlaylistInfoStruct(
              playlistInfo
                .getVideoInfoList()
                .parallelStream()
                .map(videoInfo -> {
                    return new VideoInfoStruct(new File(folder, videoInfo
                      .getId()
                      .concat(DEFAULT_FILE_EXTENSION)),
                      videoInfo
                    );
                }).collect(Collectors.toList())
            );

            queue.addAll(playlistInfoStruct.getVideoInfoList());
            return playlistInfoStruct;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public CompletableFuture<PlaylistInfoStruct> requestAsynchronousSong(@NonNull String url, int quality) {
        return CompletableFuture
          .supplyAsync(() -> requestSong(url, quality), EntityMusicManager.getPollService());
    }

    @Override
    public PlaylistInfoStruct requestSong(@NonNull String url) {
        return requestSong(url, DEFAULT_STACK_QUALITY);
    }

    @Override
    public CompletableFuture<PlaylistInfoStruct> requestAsynchronousSong(@NonNull String url) {
        return requestAsynchronousSong(url, DEFAULT_STACK_QUALITY);
    }
}
