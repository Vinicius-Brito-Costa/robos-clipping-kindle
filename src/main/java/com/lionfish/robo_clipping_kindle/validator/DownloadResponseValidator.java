package com.lionfish.robo_clipping_kindle.validator;

import com.lionfish.robo_clipping_kindle.domain.response.DownloadResponse;

public class DownloadResponseValidator implements IValidator{

    @Override
    public boolean validate(Object validatedValue) {
        DownloadResponse response = (DownloadResponse) validatedValue;
        return response != null && response.getResult() != null
                && response.getClippingCount() > 0
                && response.getBookCount() > 0;
    }
}
