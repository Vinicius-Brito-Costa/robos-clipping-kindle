package com.lionfish.robo_clipping_kindle.validator;

import com.lionfish.robo_clipping_kindle.domain.book.Library;
import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import com.lionfish.robo_clipping_kindle.domain.response.DownloadResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DownloadResponseValidatorTest {

    @Test
    void validResponse(){
        DownloadResponseValidator downloadResponseValidator = (DownloadResponseValidator) ValidatorMapEnum.loadValidator(CommandType.DOWNLOAD);
        DownloadResponse response = new DownloadResponse(1, 1, new Library());
        Assertions.assertNotNull(downloadResponseValidator);
        Assertions.assertTrue(downloadResponseValidator.validate(response));
    }

    @Test
    void invalidResponse(){
        DownloadResponseValidator downloadResponseValidator = (DownloadResponseValidator) ValidatorMapEnum.loadValidator(CommandType.DOWNLOAD);
        Assertions.assertNotNull(downloadResponseValidator);

        DownloadResponse zeroBooks = new DownloadResponse(0, 1, new Library());
        Assertions.assertFalse(downloadResponseValidator.validate(zeroBooks));

        DownloadResponse zeroClippings = new DownloadResponse(1, 0, new Library());
        Assertions.assertFalse(downloadResponseValidator.validate(zeroClippings));

        DownloadResponse nullResult = new DownloadResponse(1, 1, null);
        Assertions.assertFalse(downloadResponseValidator.validate(nullResult));

        Assertions.assertFalse(downloadResponseValidator.validate(null));
    }
}
