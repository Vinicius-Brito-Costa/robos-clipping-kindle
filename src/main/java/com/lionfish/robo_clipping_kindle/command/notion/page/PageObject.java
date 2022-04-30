package com.lionfish.robo_clipping_kindle.command.notion.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lionfish.robo_clipping_kindle.command.notion.block.IBlock;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageObject implements IBasicIdentification{

    String object;
    String id;
    String createdTime;
    PartialUser createdBy;
    String lastEditedTime;
    PartialUser lastEditedBy;
    boolean archived;
    IFileObject icon;
    IFileObject cover;
    Property properties;
    IParent parent;
    IBlock[] children;
}
