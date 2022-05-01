package com.lionfish.robo_clipping_kindle.command.notion.block;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lionfish.robo_clipping_kindle.command.notion.page.Title;
import lombok.Getter;

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
