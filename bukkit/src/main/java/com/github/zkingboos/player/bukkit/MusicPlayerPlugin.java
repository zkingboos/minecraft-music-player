package com.github.zkingboos.player.bukkit;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.github.zkingboos.player.bukkit.command.MusicPlayerCommand;
import com.github.zkingboos.player.bukkit.manager.EntityMusicManager;
import com.github.zkingboos.player.bukkit.operation.OperationResolver;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MusicPlayerPlugin extends JavaPlugin {

    @Override
    @SneakyThrows
    public void onLoad() {
        saveDefaultConfig();
        OperationResolver.prepareBinaryFolder(getDataFolder());
    }

    @SneakyThrows
    @Override
    public void onEnable() {
        final EntityMusicManager entityMusicManager = new EntityMusicManager(this);
        DbxRequestConfig config = DbxRequestConfig
          .newBuilder("zkingboos/minecraft-music-player")
          .build();

        final DbxClientV2 clientV2 = new DbxClientV2(config, System.getenv("DROPBOX_TOKEN"));
        System.out.println("clientV2.users().getCurrentAccount().getName() = " + clientV2.users().getCurrentAccount().getName());
        final MusicPlayerCommand executor = new MusicPlayerCommand(entityMusicManager, clientV2);
        getCommand("music-player")
          .setExecutor(executor);

        Bukkit.getPluginManager().registerEvents(executor, this);
    }

    @Override
    public void onDisable() {
        OperationResolver.shutdownOperation();
        EntityMusicManager.closePollService();
    }
}
