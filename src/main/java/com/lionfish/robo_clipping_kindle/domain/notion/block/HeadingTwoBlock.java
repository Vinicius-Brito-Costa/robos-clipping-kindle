package com.lionfish.robo_clipping_kindle.domain.notion.block;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lionfish.robo_clipping_kindle.domain.notion.page.Title;
import lombok.Getter;

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
