package com.lionfish.robo_clipping_kindle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lionfish.robo_clipping_kindle.command.notion.BuildPage;
import com.lionfish.robo_clipping_kindle.command.notion.block.CalloutBlock;
import com.lionfish.robo_clipping_kindle.command.notion.block.HeadingTwoBlock;
import com.lionfish.robo_clipping_kindle.command.notion.block.IBlock;
import com.lionfish.robo_clipping_kindle.command.notion.block.Text;
import com.lionfish.robo_clipping_kindle.command.notion.page.PageObject;
import com.lionfish.robo_clipping_kindle.command.notion.page.PageObjectDTO;
import com.lionfish.robo_clipping_kindle.command.notion.page.Title;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseData;
import com.lionfish.robo_clipping_kindle.domain.book.BookClippings;
import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import com.lionfish.robo_clipping_kindle.domain.request.IntegrationRequestDTO;
import com.lionfish.robo_clipping_kindle.util.FeignNotionUtil;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.httpclient.ApacheHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NotionService {

    private static final Logger logger = LoggerFactory.getLogger(NotionService.class);
    private static final String TEXT = "text";
    private static final int MAX_BLOCK_COUNT = 999;
    private static final int MAX_CALLOUT_TEXT_LENGTH = 2000;
    private static final int MAX_CALLOUT_COUNT = 10;
    private static final String NOTION_URL = "https://api.notion.com/v1";
    private static final Integer[] BOOK_EMOTICONS = new Integer[]{0x1F4D4, 0x1F4D5, 0x1F4D6, 0x1F4D7, 0x1F4D8, 0x1F4D9, 0x1F4DA, 0x1F4D3, 0x1F4D2, 0x1F4C3, 0x1F4DC, 0x1F4C4, 0x1F4F0, 0x1F5DE, 0x1F4D1};

    private NotionService(){}

    public static ResponseData buildResponseMessage(CommandType type, String command, IntegrationRequestDTO request){
        return CommandService.buildResponse(type, command, request);
    }

    /***
     * Uploads clippings on notion, each book will create a page containing all it`s clippings.
     * If the book clippings exceed the limit of notion, a separeted call will be made to add the exceeding blocks.
     * @param bookClippings List of BookClippings
     * @param parentPageId The pageId that was shared by the user in the frontend
     * @param notionToken Token used to authorize the action in notion.
     */
    public static void uploadBookPages(List<BookClippings> bookClippings, String parentPageId, String notionToken){
        FeignNotionUtil notion = getNotionClient();
        ObjectMapper mapper = new ObjectMapper();
        for(BookClippings clippings : bookClippings){
            BuildPage pageBuilder = new BuildPage();

            logger.info("[Message] Creating new page");
            pageBuilder.setParent(parentPageId);
            pageBuilder.setTitle(clippings.getTitle());
            pageBuilder.setIcon(readEmoticon(BOOK_EMOTICONS[new Random().nextInt(BOOK_EMOTICONS.length)]));

            List<IBlock> blocks = generateBlocks(clippings.getClippings());

            pageBuilder.setChildren(blocks.subList(0, Math.min(MAX_BLOCK_COUNT, blocks.size())).toArray(IBlock[]::new));
            PageObject page = pageBuilder.build();

            try{
                PageObjectDTO response = notion.createPage(notionToken, mapper.writeValueAsString(page));
                if(blocks.size() > MAX_BLOCK_COUNT){
                    logger.info("[Message] Appending extra blocks to page {{}}", clippings.getTitle());
                    String children = "{\"children\":" + mapper.writeValueAsString(blocks.subList(MAX_BLOCK_COUNT, blocks.size())) + "}";
                    notion.addBlockToPage(notionToken, response.getId(), children);
                }
            } catch (JsonProcessingException e) {
                logger.error("[Error] Error trying to create/update page", e);
            }
        }
    }

    /***
     * Build notion feign client
     * @return notion client
     */
    public static FeignNotionUtil getNotionClient(){
        return Feign
                .builder()
                .client(new ApacheHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(FeignNotionUtil.class, NOTION_URL);
    }

    /***
     * Create blocks based on clippings. Currently, is using two types of blocks: HeadingTwo(Page, Position, Date) and Callout(Highlight)
     * @param clippings List of Clippings
     * @return List of IBlocks
     */
    public static List<IBlock> generateBlocks(List<Clipping> clippings){
        List<IBlock> blocks = new ArrayList<>();
        int count = 1;
        logger.info("[Message] Creating blocks from clippings");
        for(Clipping clipping : clippings){
            String heading = count + createHeading(clipping);
            HeadingTwoBlock headingTwoBlock = new HeadingTwoBlock(new Title[]{new Title(TEXT, new Text(heading))});
            blocks.add(headingTwoBlock);
            String highlight = clipping.getHighlight();
            double calloutCount = getCalloutCount(highlight);
            if(calloutCount > 1){
                logger.info("[Message] Clipping {{}} has more than one callout. Total callout blocks: {{}}", heading, calloutCount);
                for(int index = 0; index < calloutCount; index++){
                    int maxLen = Math.min(MAX_CALLOUT_TEXT_LENGTH * (index + 1), highlight.length());
                    CalloutBlock callout = new CalloutBlock(new Title[]{new Title(TEXT, new Text(highlight.substring(MAX_CALLOUT_TEXT_LENGTH * index, maxLen)))});
                    blocks.add(callout);
                }
            }
            else{
                CalloutBlock callout = new CalloutBlock(new Title[]{new Title(TEXT, new Text(clipping.getHighlight()))});
                blocks.add(callout);
            }
            count++;
        }
        logger.info("[Message] Blocks created. Total: {{}}", blocks.size());
        return blocks;
    }

    /***
     * Creates a header based on what`s available on clipping
     * @param clipping Clipping
     * @return String containing header: ex: Page: 1 Postion: 32 Date: 12/22/2022
     */
    private static String createHeading(Clipping clipping){
        List<String> info = new ArrayList<>(Arrays.asList(
                appendIfNotBlank(clipping.getPage(), "Page: "),
                appendIfNotBlank(clipping.getPosition(), "Position: "),
                appendIfNotBlank(clipping.getDate().toString(), "Date: ")
        ));
        info.removeAll(Arrays.asList("", null));

        return ") " + String.join(" | ", info);
    }
    private static String appendIfNotBlank(String text, String beforeText){
        if(text != null && beforeText != null){
            if(!text.isBlank() && !beforeText.isBlank()){
                return beforeText + text;
            }
        }
        return "";
    }
    private static String appendIfNotBlank(Integer text, String beforeText){

        if(text != null && beforeText != null){
            String strText = String.valueOf(text);
            if(!strText.isBlank() && !beforeText.isBlank()){
                return beforeText + strText;
            }
        }
        return "";
    }

    private static double getCalloutCount(String highlight){
        return Math.min(Math.ceil(highlight.length() / (double) MAX_CALLOUT_TEXT_LENGTH), MAX_CALLOUT_COUNT);
    }

    private static String readEmoticon(Integer point){
        return new StringBuilder().appendCodePoint(point).toString();
    }

}
