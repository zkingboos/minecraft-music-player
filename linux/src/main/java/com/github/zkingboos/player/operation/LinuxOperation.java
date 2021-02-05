package com.github.zkingboos.player.operation;

import com.github.zkingboos.player.bukkit.operation.Operation;
import org.apache.commons.lang.SystemUtils;

import java.io.IOException;

public final class LinuxOperation implements Operation {

    @Override
    public String getOperation() {
        return "Linux";
    }

    @Override
    public String getArtifact() {
        return "linux.zip";
    }

    @Override
    public String getEnvironmentVariable() {
        return "PATH=%s:$PATH";
    }

    @Override
    public String getExecutableCommand() {
        return "bash -c youtube-dl";
    }

    @Override
    public boolean isSupported() {
        return SystemUtils.IS_OS_LINUX;
    }
}
