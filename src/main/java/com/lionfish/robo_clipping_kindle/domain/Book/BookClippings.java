package com.lionfish.robo_clipping_kindle.domain.Book;

import java.util.List;

import com.lionfish.robo_clipping_kindle.domain.Clipping.Clipping;

import lombok.Data;

@Data
public class BookClippings {
    private String title;
    private Integer clippingCount;
    private List<Clipping> clippings;
}
