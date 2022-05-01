package com.lionfish.robo_clipping_kindle.command.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lionfish.robo_clipping_kindle.command.notion.block.IBlock;
import lombok.Data;

import java.util.List;

@Data
public class ExtraBlocks {
    private int currentCount = 0;
    @JsonProperty("children")
    private List<IBlock> blocks;
}
