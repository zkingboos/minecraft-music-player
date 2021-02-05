package com.github.zkingboos.player.operation;

import com.github.zkingboos.player.bukkit.operation.Operation;
import lombok.NonNull;
import org.apache.commons.lang.SystemUtils;

import java.util.Map;

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
    public void setEnvironment(@NonNull Map<String, String> environment, @NonNull String path) {
        environment.put("PATH", environment.get("PATH") + ":" + path);
    }

    @Override
    public String[] getExecutableCommand() {
        return new String[]{"youtube-dl", "%s"};
    }

    @Override
    public boolean isSupported() {
        return SystemUtils.IS_OS_LINUX;
    }
}
