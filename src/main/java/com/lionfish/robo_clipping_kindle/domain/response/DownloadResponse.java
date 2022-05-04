package com.lionfish.robo_clipping_kindle.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;

@Data
@AllArgsConstructor
@Generated
public class DownloadResponse {
    private int bookCount;
    private int clippingCount;
    private Object result;
}
