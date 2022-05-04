package com.lionfish.robo_clipping_kindle.validator;

import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidatorMapEnumTest {

    @Test
    void loadValidValidator(){
        CommandValidator commandValidator = (CommandValidator) ValidatorMapEnum.loadValidator(CommandType.COMMAND);
        Assertions.assertNotNull(commandValidator);

        DownloadResponseValidator downloadResponseValidator = (DownloadResponseValidator) ValidatorMapEnum.loadValidator(CommandType.DOWNLOAD);
        Assertions.assertNotNull(downloadResponseValidator);

        IntegrationResponseValidator integrationResponseValidator = (IntegrationResponseValidator) ValidatorMapEnum.loadValidator(CommandType.INTEGRATION);
        Assertions.assertNotNull(integrationResponseValidator);
    }

    @Test
    void loadInvalidValidator(){
        IValidator invalidValidator = ValidatorMapEnum.loadValidator(CommandType.INTERNAL);
        Assertions.assertNull(invalidValidator);

        ValidatorMapEnum.addValidator(CommandType.INTERNAL, "ErrorClass");

        IValidator noClassFoundValidator = ValidatorMapEnum.loadValidator(CommandType.INTERNAL);
        Assertions.assertNull(noClassFoundValidator);
    }
}
