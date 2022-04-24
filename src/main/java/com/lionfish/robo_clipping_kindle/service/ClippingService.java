package com.lionfish.robo_clipping_kindle.service;

import com.lionfish.robo_clipping_kindle.domain.book.BookClippings;
import com.lionfish.robo_clipping_kindle.domain.book.Books;
import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import com.lionfish.robo_clipping_kindle.service.template.DefaultClippingTemplate;
import com.lionfish.robo_clipping_kindle.service.template.IClippingTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Holds all operations involving clippings, mainly format and segregate
 */
public class ClippingService {

    private static final Logger logger = LoggerFactory.getLogger(ClippingService.class);
    public static final String CLIP_SEPARATOR = "==========";
    public static final String BREAK_ROW = "\n";

    private ClippingService(){}

    /***
     * Clean, format and join(by book title) clippings
     * @param clippings String that represents 'My Clippings' file
     * @return Books containing all the clippings extracted from the provided String
     */
    public static Books getBooksWithClippings(String clippings){
        IClippingTemplate template = new DefaultClippingTemplate();
        logger.info("[Message] Clipping template: Default");
        HashMap<String, List<Clipping>> fullBookClippings = new HashMap<>();
        int totalClippings = 0;
        for(List<String> clip : ClippingService.getClippings(clippings)){
            List<String> cleanedClipping = ClippingService.removeEmptyBlankAndInvalid(clip);
            if(ClippingService.removeClipping(cleanedClipping)){
                logger.debug("[Debug] Clipping removed.");
                continue;
            }
            Clipping formattedClipping = template.formatClipping(cleanedClipping);
            String clippingTitle = formattedClipping.getTitle();
            List<Clipping> currentClippings = fullBookClippings.get(clippingTitle);
            if(currentClippings != null){
                currentClippings.add(formattedClipping);
                totalClippings++;
                continue;
            }
            currentClippings = new ArrayList<>();
            currentClippings.add(formattedClipping);
            fullBookClippings.put(clippingTitle, currentClippings);
            totalClippings++;
        }
        logger.info("[Message] Total books: {}", fullBookClippings.size());
        logger.info("[Message] Total clippings: {}", totalClippings);
        List<BookClippings> bookClippings = new ArrayList<>();
        for(Map.Entry<String, List<Clipping>> entry : fullBookClippings.entrySet()){
            List<Clipping> clipps = entry.getValue();
            bookClippings.add(new BookClippings(entry.getKey(), clipps.size(), clipps));
        }
        return new Books(fullBookClippings.size(), totalClippings, bookClippings);
    }

    /**
     * Separate individual clippings as single String
     * @return String List: List containing clippings
     */
    public static List<List<String>> getClippings(String clippings){
        List<List<String>> clippsAndLines = new ArrayList<>();
        for(String clipp : splitClippings(clippings)){
            clippsAndLines.add(Arrays.asList(clipp.split(BREAK_ROW)));
        }
        return clippsAndLines;
    }

    /**
     * Verify if clipping should be removed.
     * It's recommended this method after cleaning the list. 
     * @param clipp list of clippings
     * @return boolean: Clipping is apt to be removed
     */
    public static boolean removeClipping(List<String> clipp){
        return clipp.size() < 3;
    }

    /**
     * Cleans provided list by removing any list item that 
     * is empty, blank or contains only \n and remove from 
     * resulting string invalid characters
     * @param list list of clips
     * @return String List
     */
    public static List<String> removeEmptyBlankAndInvalid(List<String> list){
        List<String> cleanedList = new ArrayList<>();
        for(String listItem : list){
            // Remove non-printable unicode values, mainly <?>
            String cleanedListItem = listItem.replaceAll("\\p{C}", "");
            if(cleanedListItem.isBlank() || BREAK_ROW.equals(cleanedListItem)){
                continue;
            }
            
            cleanedList.add(cleanedListItem);
        }
        return cleanedList;
    }

    public static String[] splitClippings(String clippings){
        return clippings.split(CLIP_SEPARATOR);
    }
}
