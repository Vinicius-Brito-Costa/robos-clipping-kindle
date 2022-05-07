package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.domain.book.Library;
import com.lionfish.robo_clipping_kindle.domain.request.IntegrationRequest;
import com.lionfish.robo_clipping_kindle.domain.response.IntegrationResponse;
import com.lionfish.robo_clipping_kindle.feign.NotionFeignClient;
import com.lionfish.robo_clipping_kindle.service.ClippingService;
import com.lionfish.robo_clipping_kindle.service.NotionService;
import com.lionfish.robo_clipping_kindle.util.EnvironmentUtil;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.httpclient.ApacheHttpClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Export clippings to Notion
 */
public class NotionCommand implements ICommand {

    private static final String NOTION_URL = "https://api.notion.com/v1";
    private NotionFeignClient notion;

    public NotionCommand(){
        notion = Feign
                .builder()
                .client(new ApacheHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(NotionFeignClient.class, NOTION_URL);
    }

    /***
     * It'll always take the last page provided by the user as reference
     * If it does not find a page that is not a database, and it's parent is not the workspace it'll throw an error
     */
    @Override
    public Object execute(Object object){
        //See: https://developers.notion.com/reference/intro
        IntegrationRequest requestDTO = (IntegrationRequest) object;
        Library library = ClippingService.getBooksWithClippings(requestDTO.getClippings());

        String encodedToken =  Base64.getEncoder().encodeToString((EnvironmentUtil.getEnvVariable("NOTION_CLIENT_ID") + ":" + EnvironmentUtil.getEnvVariable("NOTION_CLIENT_PASSWORD")).getBytes(StandardCharsets.UTF_8));
        String notionToken = NotionService.getToken(encodedToken, requestDTO.getClientToken(), notion);
        String pageId = NotionService.getPageID(notionToken, notion);

        NotionService.uploadBookPages(library.getBookClippings(), pageId, notionToken, notion);

        return new IntegrationResponse(library.getBookCount(), library.getTotalClippingCount());
    }
}
