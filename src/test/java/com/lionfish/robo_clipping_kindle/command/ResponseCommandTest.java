package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.controller.response.ResponseData;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseMap;
import com.lionfish.robo_clipping_kindle.domain.response.ResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class ResponseCommandTest {

    @Test
    void responseCommandIsOK(){
        ResponseCommand responseCommand = new ResponseCommand();
        ResponseData responseData = new ResponseData(ResponseMap.OK);
        ResponseEntity<Object> response = responseCommand.execute(responseData);
        ResponseDTO responseDTO = (ResponseDTO) response.getBody();
        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(responseDTO.getCode(), responseData.getCode());
        Assertions.assertEquals(responseDTO.getData(), responseData.getBody());
    }
}
