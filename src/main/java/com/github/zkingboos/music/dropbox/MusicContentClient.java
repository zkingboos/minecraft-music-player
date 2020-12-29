/*
 * Copyright (c) 2020 yking-projects
 */

package com.github.zkingboos.music.dropbox;

import com.dropbox.core.v2.DbxClientV2;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;

public final class MusicContentClient extends DbxClientV2 {

    public MusicContentClient(@NonNull Plugin plugin) {
        super(new MusicConfiguration(plugin), new MusicCredential(plugin));
    }
}
