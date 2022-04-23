package com.lionfish.robo_clipping_kindle.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenResponseDTO {
    private String token;
    private boolean valid;
}
