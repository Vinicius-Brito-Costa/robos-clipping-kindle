package com.lionfish.robo_clipping_kindle.domain.notion.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lionfish.robo_clipping_kindle.domain.notion.block.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageObject implements IBasicIdentification {

    String object;
    String id;
    @JsonProperty("created_time")
    String createdTime;
    @JsonProperty("created_by")
    PartialUser createdBy;
    @JsonProperty("last_edited_time")
    String lastEditedTime;
    @JsonProperty("last_edited_by")
    PartialUser lastEditedBy;
    boolean archived;
    IFileObject icon;
    IFileObject cover;
    Property properties;
    IParent parent;
    IBlock[] children;
}
