package com.lionfish.robo_clipping_kindle.domain.notion.block;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;
import lombok.Getter;

@Generated
public class HeadingTwoBlock implements IBlock{

    @Getter
    private final String object = "block";
    @Getter
    private final String type = "heading_2";
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
