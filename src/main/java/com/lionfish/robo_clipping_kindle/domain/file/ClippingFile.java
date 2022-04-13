package com.lionfish.robo_clipping_kindle.domain.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClippingFile {
    private String token;
    private String clippings;
}
