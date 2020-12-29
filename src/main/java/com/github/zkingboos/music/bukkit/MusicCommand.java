/*
 * Copyright (c) 2020 yking-projects
 */

package com.github.zkingboos.music.bukkit;

import com.github.zkingboos.music.player.MusicPlayer;
import com.github.zkingboos.music.player.MusicPlayerRegistry;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class MusicCommand implements CommandExecutor {

    private final MusicPlayerRegistry playerRegistry;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Player player = (Player) sender;
        final MusicPlayer musicPlayer = playerRegistry.getRegistry(player);

        if (args.length < 1) {
            player.sendMessage("Você precisa especificar uma musica");
            return true;
        }

        final String fullMessage = String.join(" ", args);
        musicPlayer.searchFor(String.format("ytsearch: %s", fullMessage));

        return true;
    }
}
