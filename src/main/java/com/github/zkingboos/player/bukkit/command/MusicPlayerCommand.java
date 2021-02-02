package com.github.zkingboos.player.bukkit.command;

import com.github.zkingboos.player.bukkit.entity.EntityMusic;
import com.github.zkingboos.player.bukkit.manager.EntityMusicManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class MusicPlayerCommand implements CommandExecutor {

    private final EntityMusicManager musicManager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players.");
            return true;
        }

        final Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage("Digite uma musica");
            return true;
        }

        final EntityMusic entityMusic = musicManager.getEntityMusic(player);
        entityMusic
          .requestAsynchronousSong(String.format("\"ytsearch: %s\"", String.join(" ", args)))
          .thenAcceptAsync(videoInfo -> {
              player.sendMessage("Downloaded " + videoInfo.getFulltitle() + " from " + videoInfo.getUploader());
          });

        //player.sendMessage(videoInfo.title);

        return true;
    }
}
