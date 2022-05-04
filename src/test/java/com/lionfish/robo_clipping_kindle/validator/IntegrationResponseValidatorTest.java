package com.lionfish.robo_clipping_kindle.validator;

import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import com.lionfish.robo_clipping_kindle.domain.response.IntegrationResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IntegrationResponseValidatorTest {

    @Test
    void validResponse(){
        IntegrationResponseValidator integrationResponseValidator = (IntegrationResponseValidator) ValidatorMapEnum.loadValidator(CommandType.INTEGRATION);
        IntegrationResponse response = new IntegrationResponse(1, 1);
        Assertions.assertNotNull(integrationResponseValidator);
        Assertions.assertTrue(integrationResponseValidator.validate(response));
    }

    @Test
    void invalidResponse(){
        IntegrationResponseValidator integrationResponseValidator = (IntegrationResponseValidator) ValidatorMapEnum.loadValidator(CommandType.INTEGRATION);
        Assertions.assertNotNull(integrationResponseValidator);

        IntegrationResponse zeroBooks = new IntegrationResponse(0, 1);
        Assertions.assertFalse(integrationResponseValidator.validate(zeroBooks));

        IntegrationResponse zeroClippings = new IntegrationResponse(1, 0);
        Assertions.assertFalse(integrationResponseValidator.validate(zeroClippings));

        Assertions.assertFalse(integrationResponseValidator.validate(null));
    }
}
