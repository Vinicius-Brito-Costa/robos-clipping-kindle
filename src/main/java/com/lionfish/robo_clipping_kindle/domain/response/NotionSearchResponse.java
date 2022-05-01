package com.lionfish.robo_clipping_kindle.domain.response;

import com.lionfish.robo_clipping_kindle.command.notion.page.PageObjectDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotionSearchResponse {
    private String object;
    private PageObjectDTO[] results;
}
