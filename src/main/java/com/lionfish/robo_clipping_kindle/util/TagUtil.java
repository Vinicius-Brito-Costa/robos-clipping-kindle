package com.lionfish.robo_clipping_kindle.util;

import java.util.Map;

public class TagUtil {

    private TagUtil(){}

    public static String createTagWithStyle(String tag, String value, String styles){
        return openTag(tag + " style=\"" + styles + "\"") + value + closeTag(tag);
    }
    public static String createTagWithOptions(String tag, String value, Map<String, String> options){
        return openOptionTag(tag, options) + value + closeTag(tag);
    }
    public static String createTag(String tag, String value){
        return openTag(tag) + value + closeTag(tag);
    }
    public static String createSingleTagWithOptions(String tag, String value, Map<String, String> options){
        return openOptionTag(tag, options) + value + ">";
    }
    public static String createSingleTag(String tag){
        return "<" + tag + ">";
    }

    private static String openOptionTag(String tag, Map<String, String> options){
        StringBuilder result = new StringBuilder("<" + tag + " ");
        for(Map.Entry<String, String> entry : options.entrySet()){
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }
        result.append(">");
        return result.toString();
    }
    private static String openTag(String tag){
        return "<" + tag + ">";
    }
    private static String closeTag(String tag){
        return "</" + tag + ">";
    }
}
