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

    public static ExecutorService getPollService() {
        return POLL_SERVICE;
    }

    private final Map<UUID, EntityMusic> cache;
    private final File musicFolder;

    public EntityMusicManager(@NonNull Plugin plugin) {
        this.cache = new LinkedHashMap<>();
        this.musicFolder = new File(plugin.getDataFolder(), "music");
    }

    public EntityMusic getEntityMusic(@NonNull Player player) {
        EntityMusic entityMusic = cache.get(player.getUniqueId());
        if (entityMusic != null) return entityMusic;

        entityMusic = new BukkitEntityMusic(musicFolder, player);
        cache.put(player.getUniqueId(), entityMusic);
        return entityMusic;
    }
}
