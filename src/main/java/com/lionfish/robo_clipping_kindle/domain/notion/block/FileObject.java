package com.lionfish.robo_clipping_kindle.domain.notion.block;

import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class FileObject implements IFileObject{

    private static final String TYPE = "external";
    private External external;

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
