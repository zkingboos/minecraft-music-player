package com.github.zkingboos.player.bukkit.manager;

import com.github.zkingboos.player.bukkit.entity.BukkitEntityMusic;
import com.github.zkingboos.player.bukkit.entity.EntityMusic;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

@Data
public class EntityMusicManager {

    private static final ExecutorService POLL_SERVICE = new ForkJoinPool(4);
    private final Map<UUID, EntityMusic> cache;
    private final File musicFolder;

    public EntityMusicManager(@NonNull Plugin plugin) {
        this.cache = new LinkedHashMap<>();
        this.musicFolder = new File(plugin.getDataFolder(), "music");
    }

    public static ExecutorService getPollService() {
        return POLL_SERVICE;
    }

    public static void closePollService() {
        POLL_SERVICE.shutdown();
    }

    public EntityMusic getEntityMusic(@NonNull Player player) {
        return cache.computeIfAbsent(
          player.getUniqueId(),
          uuid -> new BukkitEntityMusic(musicFolder, player)
        );
    }
}
