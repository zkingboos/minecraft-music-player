package com.github.zkingboos.player;

import com.github.zkingboos.player.bukkit.MusicPlayerPlugin;
import com.github.zkingboos.player.operation.LinuxOperation;

public final class LinuxMusicPlugin extends MusicPlayerPlugin {

    @Override
    public void onEnable() {
        setOperation(new LinuxOperation());
        super.onEnable();
    }
}

