package com.github.zkingboos.player.bukkit.operation;

import lombok.NonNull;

import java.io.Closeable;
import java.util.Map;

public interface Operation extends Closeable {

    String getOperation();

    String getArtifact();

    void setEnvironment(@NonNull Map<String, String> environment, @NonNull String path);

    String[] getExecutableCommand();

    boolean isSupported();

    @Override
    default void close() {
    }
}
