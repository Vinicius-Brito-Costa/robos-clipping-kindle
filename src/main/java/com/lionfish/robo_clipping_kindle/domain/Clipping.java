package com.lionfish.robo_clipping_kindle.domain;

import java.util.Date;

import lombok.Data;

/**
 * Clipping/Highlight contains two main informations:
 *  - The first one contains clipping data: Date, page and position;
 *  - Second contains the actual clipping/highlight.
 */
@Data
public class Clipping {

    private String title;
    private Integer page;
    private String position;
    private Date date;
    private String highlight;

}
