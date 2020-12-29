/*
 * Copyright (c) 2020 yking-projects
 */

package com.github.zkingboos.music;

import com.github.zkingboos.music.bukkit.MusicCommand;
import com.github.zkingboos.music.player.MusicPlayer;
import com.github.zkingboos.music.player.MusicPlayerRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class MusicPlugin extends JavaPlugin {

    private MusicPlayerRegistry playerRegistry;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        MusicPlayer.liveItUp();

//        MusicContentClient musicClient = new MusicContentClient(this);
        this.playerRegistry = new MusicPlayerRegistry();
        getCommand("music").setExecutor(new MusicCommand(playerRegistry));
    }

    @Override
    public void onDisable() {
        MusicPlayer.fuckItUp(playerRegistry);
    }
}
