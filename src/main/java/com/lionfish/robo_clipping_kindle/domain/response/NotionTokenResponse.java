package com.lionfish.robo_clipping_kindle.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lionfish.robo_clipping_kindle.domain.notion.block.Owner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class NotionTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("bot_id")
    private String botId;
    @JsonProperty("workspace_name")
    private String workspaceName;
    @JsonProperty("workspace_icon")
    private String workspaceIcon;
    @JsonProperty("workspace_id")
    private String workspaceId;
    private Owner owner;
}
