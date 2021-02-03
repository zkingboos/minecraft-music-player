package com.github.zkingboos.player.bukkit.struct;

import com.sapher.youtubedl.mapper.PlaylistInfo;
import com.sapher.youtubedl.mapper.VideoInfo;
import com.sapher.youtubedl.mapper.YoutubePlaylistInfo;
import lombok.NonNull;

import java.util.List;

public class PlaylistInfoStruct extends YoutubePlaylistInfo<VideoInfoStruct> {

    public PlaylistInfoStruct(@NonNull List<VideoInfoStruct> videoInfoList) {
        super(videoInfoList);
    }
}
