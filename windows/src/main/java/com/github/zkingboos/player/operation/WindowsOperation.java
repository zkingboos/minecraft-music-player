package com.github.zkingboos.player.operation;

import com.github.zkingboos.player.bukkit.operation.Operation;
import lombok.NonNull;
import org.apache.commons.lang.SystemUtils;

import java.util.Map;

public final class WindowsOperation implements Operation {

    @Override
    public String getOperation() {
        return "Windows";
    }

    @Override
    public String getArtifact() {
        return "windows.zip";
    }

    @Override
    public void setEnvironment(@NonNull Map<String, String> environment, @NonNull String path) {
        environment.put("Path", path);
    }

    @Override
    public String[] getExecutableCommand() {
        return new String[]{"cmd.exe", "/c", "youtube-dl %s"};
    }

    @Override
    public boolean isSupported() {
        return SystemUtils.IS_OS_WINDOWS;
    }
}
