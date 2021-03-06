package com.lionfish.robo_clipping_kindle.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lionfish.robo_clipping_kindle.domain.book.Book;
import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import com.lionfish.robo_clipping_kindle.domain.notion.NotionPageBuilder;
import com.lionfish.robo_clipping_kindle.domain.notion.UploadedPages;
import com.lionfish.robo_clipping_kindle.domain.notion.block.*;
import com.lionfish.robo_clipping_kindle.domain.notion.page.PageObject;
import com.lionfish.robo_clipping_kindle.domain.notion.page.PageObjectDTO;
import com.lionfish.robo_clipping_kindle.domain.response.NotionTokenResponse;
import com.lionfish.robo_clipping_kindle.feign.NotionFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class NotionService {

    private static final Logger logger = LoggerFactory.getLogger(NotionService.class);
    private static final String TEXT = "text";
    public static final int MAX_BLOCK_COUNT = 999;
    public static final int MAX_CALLOUT_TEXT_LENGTH = 2000;
    public static final int MAX_CALLOUT_COUNT = 10;
    private static final Integer[] BOOK_EMOTICONS = new Integer[]{0x1F4D4, 0x1F4D5, 0x1F4D6, 0x1F4D7, 0x1F4D8, 0x1F4D9, 0x1F4DA, 0x1F4D3, 0x1F4D2, 0x1F4C3, 0x1F4DC, 0x1F4C4, 0x1F4F0, 0x1F5DE, 0x1F4D1};

    private NotionService(){}

    public static String getPageID(String notionBearerToken, NotionFeignClient notion){
        String pageId = "";

        for(PageObjectDTO result : notion.search(notionBearerToken, "page", "object").getResults()){
            if(result.getParent().isWorkspace()){
                pageId = result.getId();
            }
        }
        return pageId;
    }

    /***
     * Uploads clippings on notion, each book will create a page containing all it`s clippings.
     * If the book clippings exceed the limit of notion, a separated call will be made to add the exceeding blocks.
     * @param bookClippings List of BookClippings
     * @param parentPageId The pageId that was shared by the user in the frontend
     * @param notionToken Token used to authorize the action in notion.
     * @return Uploaded pages count
     */
    public static UploadedPages uploadBookPages(List<Book> bookClippings, String parentPageId, String notionToken, NotionFeignClient notion){
        ObjectMapper mapper = new ObjectMapper();
        int pageCount = 0;
        int blockCount = 0;
        int clippingCount = 0;
        for(Book clippings : bookClippings){
            NotionPageBuilder pageBuilder = new NotionPageBuilder();

            logger.info("[Message] Creating new page");
            pageBuilder.setParent(parentPageId);
            pageBuilder.setTitle(clippings.getTitle());
            pageBuilder.setIcon(readEmoticon(BOOK_EMOTICONS[new Random().nextInt(BOOK_EMOTICONS.length)]));

            List<IBlock> blocks = generateBlocks(clippings.getClippings());

            pageBuilder.setChildren(blocks.subList(0, Math.min(MAX_BLOCK_COUNT, blocks.size())).toArray(IBlock[]::new));
            PageObject page = pageBuilder.build();

            try{
                PageObjectDTO response = notion.createPage(notionToken, mapper.writeValueAsString(page));
                pageCount++;
                if(blocks.size() > MAX_BLOCK_COUNT){
                    logger.info("[Message] Appending extra blocks to page {{}}", clippings.getTitle());
                    String children = "{\"children\":" + mapper.writeValueAsString(blocks.subList(MAX_BLOCK_COUNT, blocks.size())) + "}";
                    notion.addBlockToPage(notionToken, response.getId(), children);
                }
                clippingCount += clippings.getClippings().size();
                blockCount += blocks.size();
            } catch (Exception e) {
                logger.error("[Error] Error trying to create/update page", e);
            }
        }
        return new UploadedPages(pageCount, blockCount, clippingCount);
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

    public static NotionTokenResponse getToken(String encodedNotionToken, String clientToken, NotionFeignClient notion){
        return notion.getToken(encodedNotionToken, clientToken, "https://robos-kindle-clipping-frontend.vercel.app/integration/notion");
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
