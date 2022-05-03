package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.domain.response.ResponseData;
import com.lionfish.robo_clipping_kindle.domain.response.ResponseMap;
import com.lionfish.robo_clipping_kindle.domain.response.DefaultResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class ResponseCommandTest {

    @Test
    void responseCommandIsOK(){
        ResponseCommand responseCommand = new ResponseCommand();
        ResponseData responseData = new ResponseData(ResponseMap.OK);
        ResponseEntity<DefaultResponse> response = responseCommand.execute(responseData);
        Assertions.assertNotNull(response);
        DefaultResponse responseDTO = response.getBody();
        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(responseDTO.getCode(), responseData.getCode());
        Assertions.assertEquals(responseDTO.getData(), responseData.getBody());
    }
}
