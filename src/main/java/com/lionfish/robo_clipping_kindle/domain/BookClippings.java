package com.lionfish.robo_clipping_kindle.domain;

import java.util.List;

import lombok.Data;

@Data
public class BookClippings {
    private String title;
    private Integer clippingCount;
    private List<Clipping> clippings;
}
