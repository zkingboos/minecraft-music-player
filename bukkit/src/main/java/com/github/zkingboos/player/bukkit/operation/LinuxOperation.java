package com.github.zkingboos.player.bukkit.operation;

import lombok.NonNull;
import org.apache.commons.lang.SystemUtils;

import java.nio.file.Path;
import java.util.Map;

public final class LinuxOperation extends Operation {

    public LinuxOperation() {
        super("Linux", "linux.zip");
    }

    @Override
    public void setEnvironment(@NonNull Map<String, String> environment, @NonNull String path) {
        environment.put("PATH", environment.get("PATH") + ":" + path);
    }

    @Override
    public String[] getExecutableCommand(@NonNull Path binaryPath) {
        return new String[]{binaryPath.resolve("youtube-dl").toFile().getAbsolutePath()};
    }

    @Override
    public boolean isSupported() {
        return SystemUtils.IS_OS_LINUX;
    }
}
