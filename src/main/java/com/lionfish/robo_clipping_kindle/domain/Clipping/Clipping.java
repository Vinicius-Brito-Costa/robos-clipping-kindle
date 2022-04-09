package com.lionfish.robo_clipping_kindle.domain.Clipping;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Clipping/Highlight contains two main informations:
 *  - The first one contains clipping data: Date, page and position;
 *  - Second contains the actual clipping/highlight.
 */
@Data
public class Clipping {

    @JsonIgnore
    private String title;
    @JsonInclude(Include.NON_NULL)
    private Integer page;
    @JsonInclude(Include.NON_NULL)
    private String position;
    private Date date;
    private String highlight;

}
