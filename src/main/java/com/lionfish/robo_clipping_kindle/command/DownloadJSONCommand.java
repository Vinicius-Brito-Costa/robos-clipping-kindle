package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.domain.book.Library;
import com.lionfish.robo_clipping_kindle.domain.response.DownloadResponse;
import com.lionfish.robo_clipping_kindle.service.ClippingService;

/**
 * Return clippings formated in a file for download
 * Clipping normally contains 3 lines:
 *  - The first one contains the book title;
 *  - Second contains clipping data: Date, page and position;
 *  - Third contains the actual clipping/highlight.
 */
public class DownloadJSONCommand implements ICommand{

    @Override
    public DownloadResponse execute(Object object) {
        Library books = ClippingService.getBooksWithClippings((String) object);
        return new DownloadResponse(books.getBookCount(), books.getTotalClippingCount(), books.getBookClippings());
    }
    
}
