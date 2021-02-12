package com.github.zkingboos.player.bukkit.struct;

import com.github.zkingboos.youtubedl.entry.playlist.RawPlaylistInfo;
import lombok.NonNull;

import java.util.List;

public class PlaylistInfoStruct extends RawPlaylistInfo<VideoInfoStruct> {

    public PlaylistInfoStruct(@NonNull List<VideoInfoStruct> videoInfoList) {
        super(videoInfoList);
    }
}
