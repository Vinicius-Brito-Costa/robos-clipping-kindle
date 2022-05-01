package com.lionfish.robo_clipping_kindle.util;

import com.lionfish.robo_clipping_kindle.command.notion.page.PageObjectDTO;
import com.lionfish.robo_clipping_kindle.domain.response.NotionSearchResponse;
import com.lionfish.robo_clipping_kindle.domain.response.NotionTokenResponse;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers({
        "Notion-Version: 2022-02-22",
        "Content-Type: application/json"
})
public interface FeignNotionUtil {

    @RequestLine("POST /oauth/token")
    @Headers({"Authorization: Basic {token}"})
    @Body("%7B\"grant_type\": \"authorization_code\", \"code\": \"{code}\", \"redirect_uri\": \"{redirectUri}\"%7D")
    NotionTokenResponse getToken(@Param("token") String token, @Param("code") String code, @Param("redirectUri") String redirectUri);

    @RequestLine("POST /search")
    @Headers("Authorization: Bearer {token}")
    @Body("%7B\"filter\":%7B\"value\":\"{value}\",\"property\":\"{property}\"%7D%7D")
    NotionSearchResponse search(@Param("token") String token, @Param("value") String value, @Param("property") String property);

    @RequestLine("GET /pages/{pageId}")
    @Headers("Authorization: Bearer {token}")
    String getPage(@Param("token") String token, @Param("pageId") String pageId);

    @RequestLine("POST /pages")
    @Headers("Authorization: Bearer {token}")
    @Body("{page}")
    PageObjectDTO createPage(@Param("token") String token, @Param("page") String page);

    @RequestLine("PATCH /blocks/{pageId}/children")
    @Headers("Authorization: Bearer {token}")
    @Body("{children}")
    Object addBlockToPage(@Param("token") String token, @Param("pageId") String pageId, @Param("children") String children);
}
