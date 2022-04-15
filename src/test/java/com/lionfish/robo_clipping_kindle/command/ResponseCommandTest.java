package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.controller.response.ResponseData;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseMap;
import com.lionfish.robo_clipping_kindle.domain.response.ResponseDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class ResponseCommandTest {

    @Test
    void responseCommandIsOK(){
        ResponseCommand responseCommand = new ResponseCommand();
        ResponseData responseData = new ResponseData(ResponseMap.OK);
        ResponseEntity<Object> response = responseCommand.execute(responseData);
        ResponseDAO responseDAO = (ResponseDAO) response.getBody();
        Assertions.assertNotNull(responseDAO);
        Assertions.assertEquals(responseDAO.getCode(), responseData.getCode());
        Assertions.assertEquals(responseDAO.getData(), responseData.getBody());
    }
}
