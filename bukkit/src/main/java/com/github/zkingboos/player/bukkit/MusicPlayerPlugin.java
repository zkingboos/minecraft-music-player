package com.github.zkingboos.player.bukkit;

import com.github.zkingboos.player.bukkit.command.MusicPlayerCommand;
import com.github.zkingboos.player.bukkit.manager.EntityMusicManager;
import com.github.zkingboos.player.bukkit.operation.Operation;
import com.github.zkingboos.player.bukkit.util.BinaryArtifact;
import com.sapher.youtubedl.YoutubeDL;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class MusicPlayerPlugin extends JavaPlugin {

    @Getter
    private Operation operation;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        contextOperationOverride(prepareBinaryFolder());
        final EntityMusicManager entityMusicManager = new EntityMusicManager(this);
        getCommand("music-player").setExecutor(new MusicPlayerCommand(entityMusicManager));
    }

    private String prepareBinaryFolder() {
        try {
            final File file = new File(getDataFolder(), "binary");
            final String absolutePath = file.getAbsolutePath();
            if (!file.exists()) {
                BinaryArtifact.download(operation, Files.createDirectory(Paths.get(absolutePath)));
            }
            return absolutePath;
        } catch (IOException exception) {
            getLogger().severe(String.format("Cannot download binary path from zkingboos/minecraft-music-player/tree/binary-repository media. - %s", exception.getMessage()));
            return null;
        }
    }

    private void contextOperationOverride(@NonNull String path) {
        if (operation != null) {
            YoutubeDL.setFunctionalEnvironment(environmentMap -> operation.setEnvironment(environmentMap, path));
            YoutubeDL.setFunctionalExecutable(() -> String.join(" ", operation.getExecutableCommand()).trim());
        }
    }

    public void setOperation(@NonNull Operation operation) throws IllegalStateException {
        if (!operation.isSupported()) {
            throw new IllegalStateException(String.format("The requested '%s' operation is not supported for actual operating system.", operation.getOperation()));
        }
        this.operation = operation;
    }

    @Override
    public void onDisable() {
        operation.close();
        EntityMusicManager.getPollService().shutdown();
    }
}
