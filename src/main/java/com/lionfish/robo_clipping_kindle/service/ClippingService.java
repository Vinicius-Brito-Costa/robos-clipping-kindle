package com.lionfish.robo_clipping_kindle.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds all operations involving clippings, mainly format and segregate
 */
public class ClippingService {

    public static final String CLIPP_SEPARATOR = "==========";
    public static final String BREAK_ROW = "\n";

    private ClippingService(){}

    /**
     * Separate indivual clippings as single String
     * @return String List: List containing clippings
     */
    public static List<List<String>> getClippings(String clippings){
        String[] clipps = clippings.split(CLIPP_SEPARATOR);
        List<List<String>> clippsAndLines = new ArrayList<>();
        for(String clipp : clipps){
            clippsAndLines.add(Arrays.asList(clipp.split(BREAK_ROW)));
        }
        return clippsAndLines;
    }

    /**
     * Verify if clipping should be removed.
     * It's recommended this method after cleaning the list. 
     * @param clipp
     * @return boolean: Clipping is apt to be removed
     */
    public static boolean removeClipping(List<String> clipp){
        return clipp.size() < 3;
    }

    /**
     * Cleans provided list by removing any list item that 
     * is empty, blank or contains only \n and remove from 
     * resulting string invalid characters
     * @param List<String>
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
}
