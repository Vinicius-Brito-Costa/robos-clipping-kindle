package com.lionfish.robo_clipping_kindle.domain.template;

import java.util.List;

import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;

/***
 * Template responsible for clippings formatting
 */
public interface IClippingTemplate {

    static final String INFO_SEPARATOR = " \\| ";
    static final String SPACE = " ";
    /**
     * Format a single clipping that is divided by lines in a List
     * @param clipping
     * @return String: Formatted clipping
     */
    Clipping formatClipping(List<String> clipping);
}
