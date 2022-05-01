package com.lionfish.robo_clipping_kindle.validator;

import com.lionfish.robo_clipping_kindle.domain.response.DownloadResponseDTO;

public class DownloadResponseValidator implements IValidator{

    @Override
    public boolean validate(Object validatedValue) {
        DownloadResponseDTO response = (DownloadResponseDTO) validatedValue;
        return response != null && response.getResult() != null
                && response.getClippingCount() > 0
                && response.getBookCount() > 0;
    }
}
