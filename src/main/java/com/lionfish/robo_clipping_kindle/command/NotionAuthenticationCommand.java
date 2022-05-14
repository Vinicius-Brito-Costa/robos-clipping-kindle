package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.domain.response.IntegrationAuthenticationResponse;
import com.lionfish.robo_clipping_kindle.util.EnvironmentUtil;

/***
 * Generate Notion integration url
 */
public class NotionAuthenticationCommand implements ICommand {
    private static final String BASE_URL = "https://api.notion.com/";
    private static final String AUTH_URL = "v1/oauth/authorize";
    private static final String REDIRECT = "https://robos-kindle-clipping-frontend.vercel.app/integration/notion";
    private static final String CLIENT_ID = "NOTION_CLIENT_ID";

    @Override
    public Object execute(Object object) {
        String url = "";
        String clientId = EnvironmentUtil.getEnvVariable(CLIENT_ID);
        url += "client_id=" + clientId;
        url += "&redirect_uri=" + REDIRECT;
        url += "&response_type=code&owner=user";
        return new IntegrationAuthenticationResponse(BASE_URL + AUTH_URL + "?" + url);
    }
}
