package com.lionfish.robo_clipping_kindle.domain.notion.block;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IParent {

    String getType();
    @JsonIgnore
    String getId();
    void setId(String id);
}
