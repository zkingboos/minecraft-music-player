package com.github.zkingboos.player.bukkit;

import com.github.zkingboos.player.bukkit.command.MusicPlayerCommand;
import com.github.zkingboos.player.bukkit.manager.EntityMusicManager;
import com.sapher.youtubedl.YoutubeDL;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MusicPlayerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        YoutubeDL.setEnvironmentVariables(
          String.format("Path=%s", new File(getDataFolder(), "bin").getAbsolutePath())
        );

        YoutubeDL.setExecutablePath("cmd.exe /c youtube-dl");
        final EntityMusicManager entityMusicManager = new EntityMusicManager(this);
        getCommand("music-player").setExecutor(new MusicPlayerCommand(entityMusicManager));
    }

    @Override
    public void onDisable() {
        EntityMusicManager.getPollService().shutdown();
    }
}
