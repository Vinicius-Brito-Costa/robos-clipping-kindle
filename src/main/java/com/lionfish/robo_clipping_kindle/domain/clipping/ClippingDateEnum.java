package com.lionfish.robo_clipping_kindle.domain.clipping;

import lombok.Generated;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Holds information and format date retrieved from clipping
 * Dates that comes from clipping are affected by language
 */
@Generated
public enum ClippingDateEnum {
    PT_BR("Adicionado: ", " de ", "D MMMM yyyy HH:mm:ss",new Locale("pt", "BR")),
    EN_US("Added on ", " ", "D MMMM yy HH:mm:ss", new Locale("en", "US"));

    String startsWith;
    String separator;
    String datePattern;
    Locale locale;

    private ClippingDateEnum(String startsWith, String separator, String datePattern, Locale locale){
        this.startsWith = startsWith;
        this.separator = separator;
        this.datePattern = datePattern;
        this.locale = locale;
    }

    public static ClippingDateEnum getClippingDate(String rawDate){
        ClippingDateEnum dateFormat = null;
        
        for(ClippingDateEnum value : ClippingDateEnum.values()){
            if(rawDate.startsWith(value.startsWith)){
                dateFormat = value;
            }
        }
        return dateFormat;
    }

    public static SimpleDateFormat getDateFormat(ClippingDateEnum dateEnum){
        return new SimpleDateFormat(dateEnum.datePattern, dateEnum.locale);
    }

    public String getSeparator(){
        return this.separator;
    }
}
