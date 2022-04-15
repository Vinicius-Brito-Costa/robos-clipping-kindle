package com.lionfish.robo_clipping_kindle.domain.book;

import java.util.List;

import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;

import lombok.Data;
import lombok.Generated;

@Data
@Generated
public class BookClippings {
    private String title;
    private Integer clippingCount;
    private List<Clipping> clippings;
}
