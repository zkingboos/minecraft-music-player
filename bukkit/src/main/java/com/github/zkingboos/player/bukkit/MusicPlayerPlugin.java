package com.github.zkingboos.player.bukkit;

import com.github.zkingboos.player.bukkit.command.MusicPlayerCommand;
import com.github.zkingboos.player.bukkit.manager.EntityMusicManager;
import com.github.zkingboos.player.bukkit.operation.OperationResolver;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

public final class MusicPlayerPlugin extends JavaPlugin {

    @Override
    @SneakyThrows
    public void onLoad() {
        saveDefaultConfig();
        OperationResolver.prepareBinaryFolder(getDataFolder());
    }

    @Override
    public void onEnable() {
        final EntityMusicManager entityMusicManager = new EntityMusicManager(this);
        getCommand("music-player").setExecutor(new MusicPlayerCommand(entityMusicManager));
    }


    @Override
    public void onDisable() {
        OperationResolver.shutdownOperation();
        EntityMusicManager.closePollService();
    }
}
