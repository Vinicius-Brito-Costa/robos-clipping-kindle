package com.lionfish.robo_clipping_kindle.command.notion.block;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lionfish.robo_clipping_kindle.command.notion.page.Title;
import lombok.Getter;

public class HeadingOneBlock implements IBlock{

    @Getter
    private static final String object = "block";
    @Getter
    private static final String type = "heading_1";
    private RichText heading = new RichText();

    public HeadingOneBlock(Title[] titles){
        this.heading.setTitles(titles);
    }

    @Override
    @JsonProperty("heading_1")
    public Object getBlock() {
        return this.heading;
    }

    @Override
    public void setBlock(Object block) {
        Title[] blocks = (Title[]) block;
        if(blocks != null){
            this.heading.setTitles(blocks);
        }
    }
}
