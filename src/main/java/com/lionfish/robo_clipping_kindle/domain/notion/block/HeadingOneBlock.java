package com.lionfish.robo_clipping_kindle.domain.notion.block;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;
import lombok.Getter;

@Generated
public class HeadingOneBlock implements IBlock{

    @Getter
    private final String object = "block";
    @Getter
    private final String type = "heading_1";
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
