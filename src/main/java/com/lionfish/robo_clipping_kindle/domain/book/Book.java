package com.lionfish.robo_clipping_kindle.domain.book;

import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class Book {
    private String title;
    private Integer clippingCount;
    private List<Clipping> clippings;
}
