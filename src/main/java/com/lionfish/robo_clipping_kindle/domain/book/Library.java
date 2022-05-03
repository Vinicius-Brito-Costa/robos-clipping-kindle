package com.lionfish.robo_clipping_kindle.domain.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Library {
    private int bookCount;
    private int totalClippingCount;
    private List<Book> bookClippings;
}
