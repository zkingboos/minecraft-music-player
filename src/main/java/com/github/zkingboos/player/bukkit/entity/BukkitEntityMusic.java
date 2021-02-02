package com.github.zkingboos.player.bukkit.entity;

import com.github.zkingboos.player.bukkit.manager.EntityMusicManager;
import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.mapper.VideoInfo;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

@Data
public class BukkitEntityMusic implements EntityMusic {

    private final static int DEFAULT_STACK_QUALITY = 250;
    private final static String DEFAULT_FILE_EXTENSION = ".ogg";

    private final Queue<VideoInfoStruct> queue;
    private final File folder;
    private final Player player;

    public BukkitEntityMusic(@NonNull File pluginFolder, @NonNull Player player) {
        this.queue = new ConcurrentLinkedQueue<>();
        this.folder = new File(pluginFolder, String.format("/%s", player.getUniqueId().toString()));
        this.player = player;
        if (!folder.exists()) folder.mkdirs();
    }

    @Override
    public VideoInfoStruct requestSong(@NonNull String url, int quality) {
        try {
            final YoutubeDLRequest request = new YoutubeDLRequest(url, folder.getAbsolutePath());
            request.setOption("id");
            request.setOption("audio-quality", quality);
            request.setOption("extract-audio");
            request.setOption("audio-format", "vorbis");
            request.setOption("format", "bestaudio");
            request.setOption("no-cache-dir");

            final VideoInfo videoInfo = YoutubeDL.getVideoInfo(request);
            final File file = new File(folder, videoInfo.getId().concat(DEFAULT_FILE_EXTENSION));
            return new VideoInfoStruct(file, videoInfo);
        } catch (YoutubeDLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public CompletableFuture<VideoInfoStruct> requestAsynchronousSong(@NonNull String url, int quality) {
        return CompletableFuture.supplyAsync(() -> {
            return requestSong(url, quality);
        }, EntityMusicManager.getPollService());
    }

    @Override
    public VideoInfoStruct requestSong(@NonNull String url) {
        return requestSong(url, DEFAULT_STACK_QUALITY);
    }

    @Override
    public CompletableFuture<VideoInfoStruct> requestAsynchronousSong(@NonNull String url) {
        return requestAsynchronousSong(url, DEFAULT_STACK_QUALITY);
    }
}
