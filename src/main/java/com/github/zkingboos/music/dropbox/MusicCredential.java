/*
 * Copyright (c) 2020 yking-projects
 */

package com.github.zkingboos.music.dropbox;

import com.dropbox.core.oauth.DbxCredential;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;

public final class MusicCredential extends DbxCredential {

    public MusicCredential(@NonNull Plugin plugin) {
        super(plugin.getConfig().getString("music.dropbox.client.token"));
    }
}
