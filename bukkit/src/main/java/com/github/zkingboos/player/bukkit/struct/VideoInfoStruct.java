package com.github.zkingboos.player.bukkit.struct;

import com.sapher.youtubedl.mapper.VideoInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Delegate;

import java.io.File;

@Data
@EqualsAndHashCode(callSuper = true)
public class VideoInfoStruct extends VideoInfo {

    private final File artifact;

    @Delegate
    private final VideoInfo videoInfo;

}
