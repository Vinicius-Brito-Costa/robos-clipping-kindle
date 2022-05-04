package com.lionfish.robo_clipping_kindle.domain.response;

import com.lionfish.robo_clipping_kindle.domain.notion.page.PageObjectDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class NotionSearchResponse {
    private String object;
    private PageObjectDTO[] results;
}
