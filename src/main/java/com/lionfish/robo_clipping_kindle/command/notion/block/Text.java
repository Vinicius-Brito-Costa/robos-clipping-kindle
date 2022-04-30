package com.lionfish.robo_clipping_kindle.command.notion.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Text {
    private String content;
    private String link = null;

    public Text(String content){
        this.content = content;
    }
}
