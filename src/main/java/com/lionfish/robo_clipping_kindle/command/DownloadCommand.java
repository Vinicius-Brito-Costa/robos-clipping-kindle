package com.lionfish.robo_clipping_kindle.command;

import java.util.ArrayList;
import java.util.List;

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
    public List<Clipping> execute(Object object) {
        IClippingTemplate template = new DefaultClippingTemplate();
        List<Clipping> formatedLines = new ArrayList<>();
        for(List<String> clipp : ClippingService.getClippings((String) object)){
            List<String> cleanedClipping = ClippingService.removeEmptyBlankAndInvalid(clipp);
            if(ClippingService.removeClipping(cleanedClipping)){
                continue;
            }

            formatedLines.add(template.formatClipping(cleanedClipping));
        }
        return formatedLines;
    }
    
}
