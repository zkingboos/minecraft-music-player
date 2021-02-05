package com.github.zkingboos.player.bukkit.operation;

import java.io.Closeable;

public interface Operation extends Closeable {

    String getOperation();

    String getArtifact();

    String getEnvironmentVariable();

    String getExecutableCommand();

    boolean isSupported();

    @Override
    default void close() {
    }
}
