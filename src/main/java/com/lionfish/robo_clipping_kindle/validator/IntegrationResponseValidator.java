package com.lionfish.robo_clipping_kindle.validator;

import com.lionfish.robo_clipping_kindle.domain.response.IntegrationResponseDTO;

public class IntegrationResponseValidator implements IValidator{

    @Override
    public boolean validate(Object validatedValue) {
        IntegrationResponseDTO response = (IntegrationResponseDTO) validatedValue;
        return response != null
                && response.getClippingCount() > 0
                && response.getBookCount() > 0;
    }
}
