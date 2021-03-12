package com.github.zkingboos.player.bukkit.command;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.github.zkingboos.player.bukkit.entity.BukkitEntityMusic;
import com.github.zkingboos.player.bukkit.manager.EntityMusicManager;
import com.github.zkingboos.player.bukkit.struct.VideoInfoStruct;
import com.github.zkingboos.player.resourcepack.ResourcePack;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import java.io.File;
import java.io.FileInputStream;

@RequiredArgsConstructor
public class MusicPlayerCommand implements CommandExecutor, Listener {

    private final EntityMusicManager musicManager;
    private final DbxClientV2 clientV2;

    @Override
    @SneakyThrows
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

        final BukkitEntityMusic entityMusic = (BukkitEntityMusic) musicManager.getEntityMusic(player);
        entityMusic
          .requestAsynchronousSong(String.join(" ", args))
          .thenAcceptAsync(playlistInfoStruct -> {
              for (VideoInfoStruct videoInfo : playlistInfoStruct) {
                  player.sendMessage("[" + videoInfo.getId() + "] Downloaded " + videoInfo.getFulltitle() + " from " + videoInfo.getUploader());
              }

              final ResourcePack resourcePack = entityMusic.writeResourcePack();

              final File file = resourcePack.getFile();
              try (FileInputStream inputStream = new FileInputStream(file)) {
                  System.out.println("resourcePack.getFile().getAbsolutePath() = " + file.getAbsolutePath());

                  final VideoInfoStruct videoInfoStruct = playlistInfoStruct.getVideoInfoList().get(0);
                  final String id = videoInfoStruct.getId().toLowerCase();
                  final FileMetadata metadata = clientV2
                    .files()
                    .uploadBuilder("/" + id + ".zip")
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(inputStream, progress -> {
                        long kb = progress / 1024;
                        long mb = kb / 1024;
                        if (mb > 0) player.sendMessage(mb + " mb");
                        else player.sendMessage(kb + "kb");
                    });

                  final SharedLinkMetadata link = clientV2.sharing().createSharedLinkWithSettings(metadata.getPathDisplay());
                  final String url = link.getUrl();
                  player.setResourcePack(url.substring(0, url.length() - 1) + "1");
              } catch (Exception exception) {
                  exception.printStackTrace();
              }
          });

        return true;
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerResourcePackStatus(PlayerResourcePackStatusEvent event) {
        if (event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
            final Player player = event.getPlayer();
            final BukkitEntityMusic entityMusic = (BukkitEntityMusic) musicManager.getEntityMusic(player);
            final VideoInfoStruct videoInfoStruct = entityMusic.getQueue().get(0);
            final String id = videoInfoStruct.getId().toLowerCase();
            System.out.println("id = " + id);
            player.playSound(player.getLocation(), "kmmusicplayer." + id, SoundCategory.MASTER, 1000, 1);
        }
    }
}
