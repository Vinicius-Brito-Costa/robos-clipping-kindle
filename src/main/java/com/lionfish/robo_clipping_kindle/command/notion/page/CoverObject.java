package com.lionfish.robo_clipping_kindle.command.notion.page;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoverObject implements IFileObject{

    @JsonProperty("type")
    private static final String TYPE = "external";
    private final External external;

    public CoverObject(String cover){
        external = new External();
        this.external.setUrl(cover);
    }
    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getObject() {
        return this.external.getUrl();
    }

    @Override
    public void setObject(String object) {
        this.external.setUrl(object);
    }
}
