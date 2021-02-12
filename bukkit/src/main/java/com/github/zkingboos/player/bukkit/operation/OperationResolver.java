package com.github.zkingboos.player.bukkit.operation;

import com.github.zkingboos.player.bukkit.util.BinaryArtifact;
import com.github.zkingboos.youtubedl.YoutubeDL;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@UtilityClass
public final class OperationResolver {

    private final Operation[] operations = new Operation[]{
      new WindowsOperation(),
      new LinuxOperation()
    };

    private final Operation context = resolveContext();

    public Operation resolveContext() throws IllegalStateException {
        for (Operation operation : operations) {
            if (operation.isSupported()) return operation;
        }
        throw new IllegalStateException("Cannot find operation support for actual operating system.");
    }

    public void prepareBinaryFolder(@NonNull File dataFolder) throws IOException {
        final File file = new File(dataFolder, "bin");
        if (!file.exists()) {
            BinaryArtifact.download(context, Files.createDirectory(file.toPath()));
        }

        YoutubeDL.setExecutablePath(context.getExecutableCommand(file.toPath()));
        YoutubeDL.setEnvironmentConsumer(environmentMap ->
          context.setEnvironment(environmentMap, file.getAbsolutePath())
        );
    }

    public void shutdownOperation() {
        context.close();
    }
}
