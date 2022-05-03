package com.lionfish.robo_clipping_kindle.domain.notion.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmojiObject implements IFileObject {

    private static final String TYPE = "emoji";
    private String emoji;

    public EmojiObject(String emoji){
        this.emoji = emoji;
    }
    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    @JsonProperty("emoji")
    public String getObject() {
        return this.emoji;
    }

    @Override
    public void setObject(String object) {
        this.emoji = object;
    }
}
