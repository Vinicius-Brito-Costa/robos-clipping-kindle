package com.lionfish.robo_clipping_kindle.command.notion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lionfish.robo_clipping_kindle.command.ICommand;
import com.lionfish.robo_clipping_kindle.command.notion.BuildPage;
import com.lionfish.robo_clipping_kindle.command.notion.block.*;
import com.lionfish.robo_clipping_kindle.command.notion.page.PageObject;
import com.lionfish.robo_clipping_kindle.command.notion.page.PageObjectDTO;
import com.lionfish.robo_clipping_kindle.command.notion.page.Title;
import com.lionfish.robo_clipping_kindle.domain.book.BookClippings;
import com.lionfish.robo_clipping_kindle.domain.book.Books;
import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import com.lionfish.robo_clipping_kindle.domain.request.IntegrationRequestDTO;
import com.lionfish.robo_clipping_kindle.domain.response.ExportResponseDTO;
import com.lionfish.robo_clipping_kindle.service.ClippingService;
import com.lionfish.robo_clipping_kindle.util.EnvironmentUtil;
import com.lionfish.robo_clipping_kindle.util.FeignNotionUtil;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.httpclient.ApacheHttpClient;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Export clippings to Notion
 */
public class NotionCommand implements ICommand {

    private static final Integer[] BOOK_EMOTICONS = new Integer[]{0x1F4D4, 0x1F4D5, 0x1F4D6, 0x1F4D7, 0x1F4D8, 0x1F4D9, 0x1F4DA, 0x1F4D3, 0x1F4D2, 0x1F4C3, 0x1F4DC, 0x1F4C4, 0x1F4F0, 0x1F5DE, 0x1F4D1};

    @Override
    public Object execute(Object object) {
        //See: https://developers.notion.com/reference/intro
        IntegrationRequestDTO requestDTO = (IntegrationRequestDTO) object;
        Books books = ClippingService.getBooksWithClippings(requestDTO.getClippings());

        List<PageObject> pageObjects = new ArrayList<>();
        FeignNotionUtil notion = getFeing();
        String clientToken =  Base64.getEncoder().encodeToString((EnvironmentUtil.getEnvVariable("NOTION_CLIENT_ID") + ":" + EnvironmentUtil.getEnvVariable("NOTION_CLIENT_PASSWORD")).getBytes(StandardCharsets.UTF_8));
        String token = notion.getToken(clientToken, requestDTO.getToken(), "https://webhook.site/46d396ce-5cfc-44b1-8afe-199ac8f17fe4").getAccess_token();
        ObjectMapper mapper = new ObjectMapper();

        for(BookClippings clippings : books.getBookClippings()){
            int count = 1;

            BuildPage pageBuilder = new BuildPage();
            List<IBlock> blocks = new ArrayList<>();
            ExtraBlocks extraBlocks = new ExtraBlocks();
            extraBlocks.setBlocks(new ArrayList<>());

            pageBuilder.setParent("65bfc865-ebfa-40e4-8a83-081620b2cf71");
            pageBuilder.setTitle(clippings.getTitle());
            pageBuilder.setIcon(readEmoticon(BOOK_EMOTICONS[new Random().nextInt(BOOK_EMOTICONS.length)]));

            for(Clipping clipping : clippings.getClippings()){
                HeadingTwoBlock headingTwoBlock = new HeadingTwoBlock(new Title[]{new Title("text", new Text(count + createHeading(clipping)))});
                if(blockSizeLimit(blocks)){
                    extraBlocks.setCurrentCount(count);
                    extraBlocks.getBlocks().add(headingTwoBlock);
                }
                else{
                    blocks.add(headingTwoBlock);
                }
                String highlight = clipping.getHighlight();
                if(highlight.length() > 2000){
                    double separatedHighlights = Math.ceil(highlight.length() / 2000.00);
                    separatedHighlights = separatedHighlights > 10 ? 10 : separatedHighlights;
                    for(int index = 0; index < separatedHighlights; index++){
                        int maxLen = Math.min(2000 * (index + 1), highlight.length());
                        CalloutBlock callout = new CalloutBlock(new Title[]{new Title("text", new Text(highlight.substring(2000 * index, maxLen)))});
                        if(blockSizeLimit(blocks)){
                            extraBlocks.setCurrentCount(count);
                            extraBlocks.getBlocks().add(callout);
                        }
                        else{
                            blocks.add(callout);
                        }
                    }
                }
                else{
                    CalloutBlock callout = new CalloutBlock(new Title[]{new Title("text", new Text(clipping.getHighlight()))});
                    if(blockSizeLimit(blocks)){
                        extraBlocks.setCurrentCount(count);
                        extraBlocks.getBlocks().add(callout);
                    }
                    else{
                        blocks.add(callout);
                    }
                }
                count++;
            }
            pageBuilder.setChildren(blocks.toArray(IBlock[]::new));

            pageObjects.add(pageBuilder.build());
            try{
                PageObjectDTO pageObjectDTO = notion.createPage(token, mapper.writeValueAsString(pageBuilder.build()));
                if(extraBlocks.getCurrentCount() > 0){
                    String children = "{\"children\":" + mapper.writeValueAsString(extraBlocks.getBlocks()) + "}";
                    notion.addBlockToPage(token, pageObjectDTO.getId(), children);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }

         return new ExportResponseDTO(books.getBookCount(), books.getTotalClippingCount(), pageObjects);
    }
    private boolean blockSizeLimit(List<IBlock> blocks){
        return blocks.size() >= 900;
    }
    private FeignNotionUtil getFeing(){
        return Feign
                .builder()
                .client(new ApacheHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(FeignNotionUtil.class, "https://api.notion.com/v1");
    }

    private String readEmoticon(Integer point){
        return new StringBuilder().appendCodePoint(point).toString();
    }
    private String createHeading(Clipping clipping){
        List<String> info = new ArrayList<>(Arrays.asList(
                appendIfNotBlank(clipping.getPage(), "Page: "),
                appendIfNotBlank(clipping.getPosition(), "Position: "),
                appendIfNotBlank(clipping.getDate().toString(), "Date: ")
        ));
        info.removeAll(Arrays.asList("", null));

        return ") " + String.join(" | ", info);
    }
    private String appendIfNotBlank(String text, String beforeText){
        if(text != null && beforeText != null){
            if(!text.isBlank() && !beforeText.isBlank()){
                return beforeText + text;
            }
        }
        return "";
    }
    private String appendIfNotBlank(Integer text, String beforeText){

        if(text != null && beforeText != null){
            String strText = String.valueOf(text);
            if(!strText.isBlank() && !beforeText.isBlank()){
                return beforeText + strText;
            }
        }
        return "";
    }
}
