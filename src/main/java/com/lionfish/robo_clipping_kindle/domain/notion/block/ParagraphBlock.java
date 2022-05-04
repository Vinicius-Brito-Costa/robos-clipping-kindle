package com.lionfish.robo_clipping_kindle.domain.notion.block;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Generated;
import lombok.Getter;

@Generated
public class ParagraphBlock implements IBlock {

    @Getter
    private final String object = "block";
    @Getter
    private final String type = "paragraph";
    private RichText paragraph = new RichText();

    public ParagraphBlock(Title[] titles){
        this.paragraph.setTitles(titles);
    }

    @Override
    @JsonProperty("paragraph")
    public Object getBlock() {
        return this.paragraph;
    }

    @Override
    public void setBlock(Object block) {
        Title[] blocks = (Title[]) block;
        if(blocks != null){
            this.paragraph.setTitles(blocks);
        }
    }
}
