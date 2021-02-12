package com.github.zkingboos.player.bukkit.operation;

import lombok.NonNull;
import org.apache.commons.lang.SystemUtils;

import java.nio.file.Path;
import java.util.Map;

public final class WindowsOperation extends Operation {

    public WindowsOperation() {
        super("Windows", "windows.zip");
    }

    @Override
    public void setEnvironment(@NonNull Map<String, String> environment, @NonNull String path) {
        environment.put("Path", path);
    }

    @Override
    public String[] getExecutableCommand(@NonNull Path binaryPath) {
        return new String[]{"cmd.exe", "/c", "youtube-dl"};
    }

    @Override
    public boolean isSupported() {
        return SystemUtils.IS_OS_WINDOWS;
    }
}
