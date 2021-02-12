package com.github.zkingboos.player.bukkit.operation;

import lombok.Data;
import lombok.NonNull;

import java.io.Closeable;
import java.nio.file.Path;
import java.util.Map;

@Data
public abstract class Operation implements Closeable {

    private final String operation;
    private final String artifact;

    public abstract void setEnvironment(@NonNull Map<String, String> environment, @NonNull String path);

    public abstract String[] getExecutableCommand(@NonNull Path binaryPath);

    public abstract boolean isSupported();

    @Override
    public void close() {
    }
}
