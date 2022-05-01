package com.lionfish.robo_clipping_kindle.command.notion;

import com.lionfish.robo_clipping_kindle.command.ICommand;
import com.lionfish.robo_clipping_kindle.command.notion.page.PageObjectDTO;
import com.lionfish.robo_clipping_kindle.domain.book.Books;
import com.lionfish.robo_clipping_kindle.domain.request.IntegrationRequestDTO;
import com.lionfish.robo_clipping_kindle.domain.response.IntegrationResponseDTO;
import com.lionfish.robo_clipping_kindle.service.ClippingService;
import com.lionfish.robo_clipping_kindle.service.NotionService;
import com.lionfish.robo_clipping_kindle.util.EnvironmentUtil;
import com.lionfish.robo_clipping_kindle.util.FeignNotionUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Export clippings to Notion
 */
public class NotionCommand implements ICommand {

    /***
     * It'll always take the last page provided by the user as reference
     * If it does not find a page that is not a database, and it's parent is not the workspace it'll throw an error
     */
    @Override
    public Object execute(Object object){
        //See: https://developers.notion.com/reference/intro
        IntegrationRequestDTO requestDTO = (IntegrationRequestDTO) object;
        Books books = ClippingService.getBooksWithClippings(requestDTO.getClippings());

        FeignNotionUtil notion = NotionService.getNotionClient();
        String clientToken =  Base64.getEncoder().encodeToString((EnvironmentUtil.getEnvVariable("NOTION_CLIENT_ID") + ":" + EnvironmentUtil.getEnvVariable("NOTION_CLIENT_PASSWORD")).getBytes(StandardCharsets.UTF_8));

        String token = notion.getToken(clientToken, requestDTO.getClientToken(), "https://webhook.site/46d396ce-5cfc-44b1-8afe-199ac8f17fe4").getAccess_token();
        String pageId = "";

        for(PageObjectDTO result : notion.search(token, "page", "object").getResults()){
            if(result.getParent().isWorkspace()){
                pageId = result.getId();
            }
        }
        NotionService.uploadBookPages(books.getBookClippings(), pageId, token);

        return new IntegrationResponseDTO(books.getBookCount(), books.getTotalClippingCount());
    }
}
