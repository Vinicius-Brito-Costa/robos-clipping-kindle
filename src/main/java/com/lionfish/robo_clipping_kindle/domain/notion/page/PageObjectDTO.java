package com.lionfish.robo_clipping_kindle.domain.notion.page;

import lombok.Data;

@Data
public class PageObjectDTO {
    String object;
    String id;
    WorkspaceParent parent;
}
