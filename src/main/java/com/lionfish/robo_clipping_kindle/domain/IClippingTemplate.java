package com.lionfish.robo_clipping_kindle.domain;

import java.util.List;

public interface IClippingTemplate {

    static final String INFO_SEPARATOR = " \\| ";
    static final String SPACE = " ";
    /**
     * Format a single clipping that is divided by lines in a List
     * @param List<String> clipping
     * @return String: Formated clipping
     */
    Clipping formatClipping(List<String> clipping);
}
