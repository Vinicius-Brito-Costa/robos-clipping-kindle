package com.lionfish.robo_clipping_kindle.domain.Clipping;

import java.text.SimpleDateFormat;
import java.util.Locale;

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
