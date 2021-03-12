package com.github.zkingboos.player.resourcepack;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Data
public class ResourcePack implements AutoCloseable {

    private static final Gson GSON = new GsonBuilder()
      .disableHtmlEscaping()
      .setPrettyPrinting()
      .create();

    private final String name;
    private final File file;
    private final FileOutputStream fileOutputStream;
    private final ZipOutputStream zipOutputStream;

    @SneakyThrows
    public ResourcePack(@NonNull File file) {
        this.file = file;
        this.name = file.getAbsolutePath();
        this.fileOutputStream = new FileOutputStream(name);
        this.zipOutputStream = new ZipOutputStream(fileOutputStream);
    }

    public ResourcePack writePackMeta(int packFormat, @NonNull String description) {
        writeString("pack.mcmeta", GSON.toJson(new PackMinecraftMeta(packFormat, description)));
        return this;
    }

    public ResourcePack writeSounds(@NonNull Set<String> files) {
        final Map<String, SoundJsonType.SoundChildrenType> map = new HashMap<>();
        for (String file : files) {
            file = file.replaceAll(".ogg", "");
            map.put("kmmusicplayer.".concat(file), new SoundJsonType.SoundChildrenType(
              "yamete kudasai", Lists.newArrayList(new SoundJsonType.SoundsType(file, true))
            ));
        }

        writeString("assets/minecraft/sounds.json", GSON.toJson(map));
        return this;
    }

    @SneakyThrows
    public ResourcePack writeFile(@NonNull String file, @NonNull File targetFile) {
        write(file, Files.readAllBytes(targetFile.toPath()));
        return this;
    }

    public ResourcePack writeAllFiles(@NonNull String folder, @NonNull Collection<File> files) {
        for (File file : files) {
            writeFile(folder + "/" + file.getName().toLowerCase(), file);
        }
        return this;
    }

    public ResourcePack writeString(@NonNull String file, @NonNull String content) {
        write(file, content.getBytes(StandardCharsets.UTF_8));
        return this;
    }

    private void write(@NonNull String file, byte[] bytes) {
        try {
            System.out.println("file = " + file);
            zipOutputStream.putNextEntry(new ZipEntry(file));
            zipOutputStream.write(bytes);
            zipOutputStream.closeEntry();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        zipOutputStream.close();
        fileOutputStream.close();
    }
}
