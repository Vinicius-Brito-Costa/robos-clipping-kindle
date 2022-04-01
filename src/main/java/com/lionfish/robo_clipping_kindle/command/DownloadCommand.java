package com.lionfish.robo_clipping_kindle.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lionfish.robo_clipping_kindle.domain.BookClippings;
import com.lionfish.robo_clipping_kindle.domain.Clipping;
import com.lionfish.robo_clipping_kindle.domain.DefaultClippingTemplate;
import com.lionfish.robo_clipping_kindle.domain.IClippingTemplate;
import com.lionfish.robo_clipping_kindle.service.ClippingService;

/**
 * Return clippings formated in a file for download
 * Clipping normally contains 3 lines:
 *  - The first one contains the book title;
 *  - Second contains clipping data: Date, page and position;
 *  - Third contains the actual clipping/highlight.
 */
public class DownloadCommand implements ICommand{

    @Override
    public HashMap<String, List<Clipping>> execute(Object object) {
        IClippingTemplate template = new DefaultClippingTemplate();
        HashMap<String, List<Clipping>> bookClippings = new HashMap<>();
        for(List<String> clipp : ClippingService.getClippings((String) object)){
            List<String> cleanedClipping = ClippingService.removeEmptyBlankAndInvalid(clipp);
            if(ClippingService.removeClipping(cleanedClipping)){
                continue;
            }
            Clipping formatedClipping = template.formatClipping(cleanedClipping);
            String clippingTitle = formatedClipping.getTitle();
            List<Clipping> currentClippings = bookClippings.get(clippingTitle);
            if(currentClippings != null){
                currentClippings.add(formatedClipping);
                continue;
            }
            currentClippings = new ArrayList<>();
            currentClippings.add(formatedClipping);
            bookClippings.put(clippingTitle, currentClippings);
        }
        return bookClippings;
    }
    
}
