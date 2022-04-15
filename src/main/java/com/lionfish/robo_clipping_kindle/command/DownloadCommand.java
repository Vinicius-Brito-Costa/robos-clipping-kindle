package com.lionfish.robo_clipping_kindle.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import com.lionfish.robo_clipping_kindle.domain.template.DefaultClippingTemplate;
import com.lionfish.robo_clipping_kindle.domain.template.IClippingTemplate;
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
        for(List<String> clip : ClippingService.getClippings((String) object)){
            List<String> cleanedClipping = ClippingService.removeEmptyBlankAndInvalid(clip);
            if(ClippingService.removeClipping(cleanedClipping)){
                logger.debug("[Debug] Clipping removed.");
                continue;
            }
            Clipping formattedClipping = template.formatClipping(cleanedClipping);
            String clippingTitle = formattedClipping.getTitle();
            List<Clipping> currentClippings = bookClippings.get(clippingTitle);
            if(currentClippings != null){
                currentClippings.add(formattedClipping);
                continue;
            }
            currentClippings = new ArrayList<>();
            currentClippings.add(formattedClipping);
            bookClippings.put(clippingTitle, currentClippings);
            totalClippings += clip.size();
        }
        logger.info("[Message] Total books: {}", bookClippings.size());
        logger.info("[Message] Total clippings: {}", totalClippings);
        return bookClippings;
    }
    
}
