package com.lionfish.robo_clipping_kindle.domain.notion.block;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class RichText {
    @JsonProperty("rich_text")
    private Title[] titles;
}
