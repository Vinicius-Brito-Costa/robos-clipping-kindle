package com.lionfish.robo_clipping_kindle.command.notion.block;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lionfish.robo_clipping_kindle.command.notion.page.Title;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToggleBlock implements IBlock{
    private static final String object = "block";
    private static final String type = "toggle";
    private RichText toggle = new RichText();
    @Getter @Setter
    private String color;
    @Getter @Setter
    private IBlock[] children;

    public ToggleBlock(Title[] titles){
        this.toggle.setTitles(titles);
    }

    @Override
    public String getObject() {
        return this.object;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    @JsonProperty("toggle")
    public Object getBlock() {
        return this.toggle;
    }

    @Override
    public void setBlock(Object block) {
        Title[] blocks = (Title[]) block;
        if(blocks != null){
            this.toggle.setTitles(blocks);
        }
    }
}
