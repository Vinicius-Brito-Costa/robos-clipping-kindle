package com.lionfish.robo_clipping_kindle.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lionfish.robo_clipping_kindle.domain.Clipping.Clipping;
import com.lionfish.robo_clipping_kindle.domain.Template.DefaultClippingTemplate;
import com.lionfish.robo_clipping_kindle.domain.Template.IClippingTemplate;
import com.lionfish.robo_clipping_kindle.service.ClippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Return clippings formated in a file for download
 * Clipping normally contains 3 lines:
 *  - The first one contains the book title;
 *  - Second contains clipping data: Date, page and position;
 *  - Third contains the actual clipping/highlight.
 */
public class DownloadCommand implements ICommand{

    private static final Logger logger = LoggerFactory.getLogger(DownloadCommand.class);

    @Override
    public HashMap<String, List<Clipping>> execute(Object object) {
        IClippingTemplate template = new DefaultClippingTemplate();
        logger.info("[Message] Clipping template: Default");
        HashMap<String, List<Clipping>> bookClippings = new HashMap<>();
        int totalClippings = 0;
        for(List<String> clipp : ClippingService.getClippings((String) object)){
            totalClippings += clipp.size();
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
        logger.info("[Message] Total books: {}", bookClippings.size());
        logger.info("[Message] Total clippings: {}", totalClippings);
        return bookClippings;
    }
    
}
