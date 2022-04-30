package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.command.notion.BuildPage;
import com.lionfish.robo_clipping_kindle.command.notion.block.*;
import com.lionfish.robo_clipping_kindle.command.notion.page.PageObject;
import com.lionfish.robo_clipping_kindle.command.notion.page.Title;
import com.lionfish.robo_clipping_kindle.domain.book.BookClippings;
import com.lionfish.robo_clipping_kindle.domain.book.Books;
import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import com.lionfish.robo_clipping_kindle.domain.response.ExportResponseDTO;
import com.lionfish.robo_clipping_kindle.service.ClippingService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Export clippings to Notion
 */
public class NotionCommand implements ICommand{

    private static final String BASE_URL = "https://api.notion.com/";
    private static final String AUTH_URL = "v1/oauth/authorize";
    private static final String TOKEN_URL = "v1/oauth/token";

    private static final Integer[] BOOK_EMOTICONS = new Integer[]{0x1F4D4, 0x1F4D5, 0x1F4D6, 0x1F4D7, 0x1F4D8, 0x1F4D9, 0x1F4DA, 0x1F4D3, 0x1F4D2, 0x1F4C3, 0x1F4DC, 0x1F4C4, 0x1F4F0, 0x1F5DE, 0x1F4D1};

    @Override
    public Object execute(Object object) {
        //See: https://developers.notion.com/reference/intro
        Books books = ClippingService.getBooksWithClippings((String) object);

        List<PageObject> pageObjects = new ArrayList<>();

        for(BookClippings clippings : books.getBookClippings()){
            int count = 1;

            BuildPage pageBuilder = new BuildPage();
            List<IBlock> blocks = new ArrayList<>();

            pageBuilder.setParent("65bfc865-ebfa-40e4-8a83-081620b2cf71");
            pageBuilder.setTitle(clippings.getTitle());
            pageBuilder.setIcon(readEmoticon(BOOK_EMOTICONS[new Random().nextInt(BOOK_EMOTICONS.length)]));

            for(Clipping clipping : clippings.getClippings()){
                HeadingTwoBlock headingTwoBlock = new HeadingTwoBlock(new Title[]{new Title("text", new Text(count + createHeading(clipping)))});
                blocks.add(headingTwoBlock);

                CalloutBlock callout = new CalloutBlock(new Title[]{new Title("text", new Text(clipping.getHighlight()))});
                blocks.add(callout);
                count++;
            }
            pageBuilder.setChildren(blocks.toArray(IBlock[]::new));

            pageObjects.add(pageBuilder.build());
        }

         return new ExportResponseDTO(books.getBookCount(), books.getTotalClippingCount(), pageObjects);
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
