package com.github.zkingboos.player.operation;

import com.github.zkingboos.player.bukkit.operation.Operation;
import org.apache.commons.lang.SystemUtils;

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
    public String getEnvironmentVariable() {
        return "Path=%s";
    }

    @Override
    public String getExecutableCommand() {
        return "cmd.exe /c youtube-dl";
    }

    @Override
    public boolean isSupported() {
        return SystemUtils.IS_OS_WINDOWS;
    }
}
