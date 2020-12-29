/*
 * Copyright (c) 2020 yking-projects
 */

package com.github.zkingboos.music.dropbox;

import com.dropbox.core.DbxRequestConfig;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;

public final class MusicConfiguration extends DbxRequestConfig {

    public MusicConfiguration(@NonNull Plugin plugin) {
        super(String.format("dropbox/%s", plugin.getName()));
    }
}
