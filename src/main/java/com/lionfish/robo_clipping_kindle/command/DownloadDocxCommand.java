package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.domain.book.BookClippings;
import com.lionfish.robo_clipping_kindle.domain.book.Books;
import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import com.lionfish.robo_clipping_kindle.domain.response.DownloadResponseDTO;
import com.lionfish.robo_clipping_kindle.service.ClippingService;
import com.lionfish.robo_clipping_kindle.util.TagUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Return clippings formatted in a file for .docx format
 * The conversion is made using html tags instead of creating a real .docx file
 * For this purpose style must be used only via inline method
 * Questions about what css styles .docx accepts? see: https://grabz.it/support/article/html-to-docx-supported-css/
 */
public class DownloadDocxCommand implements ICommand{

    private static final Logger logger = LoggerFactory.getLogger(DownloadDocxCommand.class);
    private static final String FONT_FAMILY = "font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif";

    @Override
    public DownloadResponseDTO execute(Object object) {
        Books books = ClippingService.getBooksWithClippings((String) object);
        StringBuilder htmlBooks = new StringBuilder();
        for (BookClippings bookClippings : books.getBookClippings()){
            htmlBooks.append(buildBook(bookClippings.getTitle(), bookClippings.getClippingCount(), bookClippings.getClippings()));
        }
        String main = TagUtil.createSingleTag("!DOCTYPE docx");
        main += TagUtil.createTag("html",
                        TagUtil.createTagWithStyle("body", htmlBooks.toString(), FONT_FAMILY));
        return new DownloadResponseDTO(books.getBookCount(), books.getTotalClippingCount(), main);
    }

    private String buildBook(String title, int bookClippingsCount, List<Clipping> clippingList){
        logger.info("[ Message ] Transforming book {{}} to html/docx...", title);
        StringBuilder clipps = new StringBuilder();
        String margin = "margin: 0;";
        clippingList.forEach(clipping -> {
            clipps.append(TagUtil.createTag("div",
                    TagUtil.createTagWithStyle("div", "Page: " + clipping.getPage(), margin + FONT_FAMILY) +
                            TagUtil.createTagWithStyle("div", "Position: " + clipping.getPosition(), margin + FONT_FAMILY) +
                            TagUtil.createTagWithStyle("div", "Date: " + clipping.getDate(), margin + FONT_FAMILY) +
                            TagUtil.createTagWithStyle("b", "\"" + clipping.getHighlight() + "\"", margin + FONT_FAMILY)));
            clipps.append(TagUtil.createSingleTag("br/"));
        });

        return TagUtil.createTagWithStyle("h1", "Book: " + title, "text-align: center;" + FONT_FAMILY) +
                    TagUtil.createTagWithStyle("h2", "Count: " + bookClippingsCount, FONT_FAMILY) +
                    TagUtil.createTagWithStyle("h2", "Highlights:", FONT_FAMILY) +
                    TagUtil.createTagWithStyle("div", clipps.toString(), "list-style: none; padding-left: 0px;" + FONT_FAMILY);
    }
}
