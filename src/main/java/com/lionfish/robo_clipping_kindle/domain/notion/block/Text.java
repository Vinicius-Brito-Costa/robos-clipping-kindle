package com.lionfish.robo_clipping_kindle.domain.notion.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class Text {
    private String content;
    private String link = null;

    public Text(String content){
        this.content = content;
    }
}
