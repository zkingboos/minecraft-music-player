package com.github.zkingboos.player.resourcepack;

import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

public class SoundJsonType extends TypeToken<Map<String, SoundJsonType.SoundChildrenType>> {

    @Data
    public static class SoundChildrenType {
        private final String description;
        private final List<SoundsType> sounds;
    }

    @Data
    public static class SoundsType {
        private final String name;
        private final boolean stream;
    }
}
