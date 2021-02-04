package com.github.zkingboos.player.bukkit.util;

import com.github.zkingboos.player.bukkit.operation.Operation;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@UtilityClass
@Log(topic = "BinaryArtifact")
public class BinaryArtifact {

    private final String baseMediaUrl = "https://media.githubusercontent.com/media/zkingboos/minecraft-music-player/binary-repository/%s";

    private String formatUrl(@NonNull Operation operation) {
        return String.format(baseMediaUrl, operation.getArtifact());
    }

    public void download(@NonNull Operation operation, @NonNull Path outputDirectory) throws IOException {
        log.info(String.format("Desired OS: %s", operation.getOperation()));

        final URL url = new URL(formatUrl(operation));
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        log.info("Getting input stream from target url...");

        try (InputStream inputStream = connection.getInputStream();
             ZipInputStream zipIn = new ZipInputStream(inputStream, StandardCharsets.UTF_8)) {
            ZipEntry entry;

            log.info("Extracting ZipInputStream to directory:");

            while ((entry = zipIn.getNextEntry()) != null) {
                log.info(String.format(" * Writing %s", entry.getName()));
                final Path resolve = outputDirectory.resolve(entry.getName());

                final byte[] buffer = new byte[4098];
                try (FileOutputStream outputStream = new FileOutputStream(resolve.toFile());
                     BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, buffer.length)) {
                    int length;
                    while ((length = zipIn.read(buffer)) > 0) {
                        bufferedOutputStream.write(buffer, 0, length);
                    }
                }
            }
        }

        log.info("Binary download and extract finished.");
    }
}
