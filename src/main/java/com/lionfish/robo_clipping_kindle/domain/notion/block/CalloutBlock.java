package com.lionfish.robo_clipping_kindle.domain.notion.block;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lionfish.robo_clipping_kindle.domain.notion.page.IFileObject;
import com.lionfish.robo_clipping_kindle.domain.notion.page.Title;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalloutBlock implements IBlock{

    @Getter
    private final String object = "block";
    @Getter
    private final String type = "callout";
    private RichText callout = new RichText();
    @Getter @Setter
    private IFileObject icon;
    @Getter @Setter
    private String color;

    public CalloutBlock(Title[] titles){
        this.callout.setTitles(titles);
    }

    @Override
    @JsonProperty("callout")
    public Object getBlock() {
        return this.callout;
    }

    @Override
    public void setBlock(Object block) {
        Title[] blocks = (Title[]) block;
        if(blocks != null){
            this.callout.setTitles(blocks);
        }
    }
}
