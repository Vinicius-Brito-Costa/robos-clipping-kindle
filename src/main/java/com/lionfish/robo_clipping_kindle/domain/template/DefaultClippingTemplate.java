package com.lionfish.robo_clipping_kindle.domain.template;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import com.lionfish.robo_clipping_kindle.domain.clipping.ClippingDateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultClippingTemplate implements IClippingTemplate{

    private static final Logger logger = LoggerFactory.getLogger(DefaultClippingTemplate.class);

    @Override
    public Clipping formatClipping(List<String> clipping) {
        Clipping createdClipping = new Clipping();
        createdClipping.setTitle(clipping.get(0));
        String[] infoList = clipping.get(1).split(INFO_SEPARATOR);

        // Check's if info list contains page information.
        if(infoList.length > 2){
            logger.debug("[Debug] Retrieving page information");
            createdClipping.setPage(Integer.parseInt(formatClippingInfo(infoList[0])));
            createdClipping.setPosition(formatClippingInfo(infoList[1]));
        }
        else if (infoList.length > 1){
            createdClipping.setPosition(formatClippingInfo(infoList[0]));
        }
        createdClipping.setDate(formatClippingDate(infoList[infoList.length - 1]));
        createdClipping.setHighlight(clipping.get(2));
        return createdClipping;
    }

    public static String formatClippingInfo(String raw){
        String[] separatedWords = raw.split(SPACE);
        return separatedWords[separatedWords.length - 1];
    }
    
    /**
     * Format clipping date string, to default Date object
     * @param rawDate raw date string  returned in 'My Clippings' file
     * @return date fate formated in Java Date()
     */
    public static Date formatClippingDate(String rawDate){
        ClippingDateEnum clippingDateEnum = ClippingDateEnum.getClippingDate(rawDate);
        String formatedDate;

        String[] splitUnformatedDate = rawDate.substring(rawDate.indexOf(", ") + 1).strip().split(clippingDateEnum.getSeparator());
        if(splitUnformatedDate.length > 4){
            splitUnformatedDate = Arrays.copyOfRange(splitUnformatedDate, 0, 4);
        }
        formatedDate = String.join(SPACE, splitUnformatedDate);
        
        SimpleDateFormat formatter = ClippingDateEnum.getDateFormat(clippingDateEnum);
        Date date = null;
        try {
            date = formatter.parse(formatedDate);
            logger.debug("[Debug] Date successfully parsed {{}}", date.toString());
        } catch (ParseException e) {
            logger.error("[Error] Failed trying to parse date {{}}", rawDate, e);
        }
        return date;
    }
}
