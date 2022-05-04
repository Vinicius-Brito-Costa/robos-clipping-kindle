package com.lionfish.robo_clipping_kindle.domain.response;

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
    private String access_token;
    private String token_type;
    private String bot_id;
    private String workspace_name;
    private String workspace_icon;
    private String workspace_id;
    private Owner owner;
}
