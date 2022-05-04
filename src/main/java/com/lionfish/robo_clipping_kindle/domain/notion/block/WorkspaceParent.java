package com.lionfish.robo_clipping_kindle.domain.notion.block;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Generated
public class WorkspaceParent{
    private static final String TYPE = "workspace_id";
    @JsonProperty("workspace")
    private boolean workspace;

    public String getType() {
        return TYPE;
    }

    public boolean isWorkspace() {
        return this.workspace;
    }

    public void setWorkspace(boolean isWorkspace) {
        this.workspace = isWorkspace;
    }
}
