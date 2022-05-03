package com.lionfish.robo_clipping_kindle.domain.notion.page;

import com.lionfish.robo_clipping_kindle.domain.notion.block.Text;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Title {
    private String type;
    private Text text;
}
