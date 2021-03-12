package com.github.zkingboos.player.resourcepack;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@Data
@ToString
public class PackMinecraftMeta {

    public PackMinecraftMeta() {
        this.pack = null;
    }

    private final PackData pack;

    public PackMinecraftMeta(int packFormat, @NonNull String description) {
        this.pack = new PackData(packFormat, description);
    }

    @Data
    public static class PackData {

        @SerializedName("pack_format")
        private final int packFormat;
        private final String description;
    }
}
