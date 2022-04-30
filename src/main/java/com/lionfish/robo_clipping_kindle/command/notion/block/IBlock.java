package com.lionfish.robo_clipping_kindle.command.notion.block;

public interface IBlock {

    String getObject();
    String getType();
    Object getBlock();
    void setBlock(Object block);
}
