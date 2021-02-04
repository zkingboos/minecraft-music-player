package com.github.zkingboos.player;

import com.github.zkingboos.player.bukkit.MusicPlayerPlugin;
import com.github.zkingboos.player.operation.WindowsOperation;

public final class WindowsMusicPlugin extends MusicPlayerPlugin {

    @Override
    public void onEnable() {
        setOperation(new WindowsOperation());
        super.onEnable();
    }
}
