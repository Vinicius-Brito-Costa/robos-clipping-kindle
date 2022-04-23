package com.lionfish.robo_clipping_kindle.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExportResponseDTO {
    private int bookCount;
    private int clippingCount;
    private Object result;
}
