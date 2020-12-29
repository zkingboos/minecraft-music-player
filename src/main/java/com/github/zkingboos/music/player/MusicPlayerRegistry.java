/*
 * Copyright (c) 2020 yking-projects
 */

package com.github.zkingboos.music.player;

import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.HashMap;

public final class MusicPlayerRegistry extends HashMap<String, MusicPlayer> {

    public MusicPlayer getRegistry(@NonNull Player player) {
        final MusicPlayer musicPlayer = get(player.getName());
        if (musicPlayer != null) return musicPlayer;
        return register(player);
    }

    public MusicPlayer register(@NonNull Player player) {
        return computeIfAbsent(player.getName(), key -> new MusicPlayer(player));
    }
}
