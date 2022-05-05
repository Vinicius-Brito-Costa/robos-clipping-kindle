package com.lionfish.robo_clipping_kindle.service;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import com.lionfish.robo_clipping_kindle.domain.response.ResponseData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class CommandServiceTest {

    private String defaultRequest = "{\"token\": \"biruleibe-1-0\",\"clippings\": \"A Philosophy of Software Design (John Ousterhout)\n- Seu destaque na página 239 | posição 3651-3684 | Adicionado: sábado, 19 de março de 2022 11:32:43\nSummary\n==========\n\"}";
    private String badRequest = "{\"token\": \"\",\"clippings\": \"\"}";

    @Test
    void buildResponse(){
        ResponseData ok = CommandService.buildResponse(CommandType.DOWNLOAD, "json", defaultRequest);
        Assertions.assertNotNull(ok);
        Assertions.assertEquals(HttpStatus.OK, ok.getStatus());

        ResponseData internal = CommandService.buildResponse(CommandType.INTERNAL, "json", defaultRequest);
        Assertions.assertNotNull(internal);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, internal.getStatus());

        ResponseData typeNull = CommandService.buildResponse(null, "json", defaultRequest);
        Assertions.assertNotNull(typeNull);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, typeNull.getStatus());

        ResponseData validator = CommandService.buildResponse(CommandType.COMMAND, "json", defaultRequest);
        Assertions.assertNotNull(validator);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, validator.getStatus());

        ResponseData wrongRequest = CommandService.buildResponse(CommandType.DOWNLOAD, "json", badRequest);
        Assertions.assertNotNull(wrongRequest);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, wrongRequest.getStatus());
    }
}
