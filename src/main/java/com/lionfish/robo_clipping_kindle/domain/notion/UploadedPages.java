package com.lionfish.robo_clipping_kindle.domain.notion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadedPages {
    private int pages;
    private int blocks;
    private int clippings;
}
