package com.lionfish.robo_clipping_kindle.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class DownloadRequestDTO {
    private String token;
    private String clippings;
}
