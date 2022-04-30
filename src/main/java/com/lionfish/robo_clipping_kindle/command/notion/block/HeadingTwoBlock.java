package com.lionfish.robo_clipping_kindle.command.notion.block;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lionfish.robo_clipping_kindle.command.notion.page.Title;
import lombok.Getter;

public class HeadingTwoBlock implements IBlock{

    @Getter
    private static final String object = "block";
    @Getter
    private static final String type = "heading_2";
    private RichText heading = new RichText();

    public HeadingTwoBlock(Title[] titles){
        this.heading.setTitles(titles);
    }

    @Override
    @JsonProperty("heading_2")
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
