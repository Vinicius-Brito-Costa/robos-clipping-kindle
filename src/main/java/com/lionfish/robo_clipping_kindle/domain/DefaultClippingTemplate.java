package com.lionfish.robo_clipping_kindle.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DefaultClippingTemplate implements IClippingTemplate{

    @Override
    public Clipping formatClipping(List<String> clipping) {
        Clipping createdClipping = new Clipping();
        createdClipping.setTitle(clipping.get(0));
        String[] infoList = clipping.get(1).split(" \\| ");
        // Check's if info list contains page information.
        if(infoList.length > 2){
            createdClipping.setPage(formatClippingPage(infoList[0]));
            createdClipping.setPosition(formatClippingPosition(infoList[1]));
        }
        else{
            createdClipping.setPosition(formatClippingPosition(infoList[0]));
        }
        createdClipping.setDate(formatClippingDate(infoList[infoList.length - 1]));
        createdClipping.setHighlight(clipping.get(2));
        return createdClipping;
    }

    public static int formatClippingPage(String raw){
        String[] separetaredWords = raw.split(" ");
        return Integer.parseInt(separetaredWords[separetaredWords.length - 1]);
    }

    public static String formatClippingPosition(String raw){
        String[] separetaredWords = raw.split(" ");
        return separetaredWords[separetaredWords.length - 1];
    }
    
    /**
     * Format clipping date string, to default Date object
     * @param date
     * @return
     */
    public static Date formatClippingDate(String rawDate){
        ClippingDateEnum clippingDateEnum = ClippingDateEnum.getClippingDate(rawDate);
        String formatedDate = "";

        String[] splitUnformatedDate = rawDate.substring(rawDate.indexOf(", ") + 1).strip().split(clippingDateEnum.getSeparator());
        if(splitUnformatedDate.length > 4){
            splitUnformatedDate = Arrays.copyOfRange(splitUnformatedDate, 0, 4);
        }
        formatedDate = String.join(" ", splitUnformatedDate);
        
        SimpleDateFormat formatter = ClippingDateEnum.getDateFormat(clippingDateEnum);
        Date date = null;
        try {
            date = formatter.parse(formatedDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
}
