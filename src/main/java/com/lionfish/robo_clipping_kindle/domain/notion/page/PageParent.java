package com.lionfish.robo_clipping_kindle.domain.notion.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lionfish.robo_clipping_kindle.domain.notion.block.IParent;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Generated
public class PageParent implements IParent {
    private static final String TYPE = "page_id";
    @JsonProperty("page_id")
    private String pageId;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getId() {
        return this.pageId;
    }

    @Override
    public void setId(String id) {
        this.pageId = id;
    }
}
