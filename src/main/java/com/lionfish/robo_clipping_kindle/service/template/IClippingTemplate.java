package com.lionfish.robo_clipping_kindle.service.template;

import java.util.List;

import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;

/***
 * Template responsible for clippings formatting
 */
public interface IClippingTemplate {

    String INFO_SEPARATOR = " \\| ";
    String SPACE = " ";
    /**
     * Format a single clipping that is divided by lines in a List
     * @param clipping List of clippings
     * @return String: Formatted clipping
     */
    Clipping formatClipping(List<String> clipping);
}
