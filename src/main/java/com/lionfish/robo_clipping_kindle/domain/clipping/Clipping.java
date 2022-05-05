package com.lionfish.robo_clipping_kindle.domain.clipping;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * Clipping/Highlight contains two main informations:
 *  - The first one contains clipping data: Date, page and position;
 *  - Second contains the actual clipping/highlight.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
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
