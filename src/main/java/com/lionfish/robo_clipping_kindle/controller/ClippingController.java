package com.lionfish.robo_clipping_kindle.controller;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.command.ICommand;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseData;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseMap;
import com.lionfish.robo_clipping_kindle.database.redis.RedisConnect;
import com.lionfish.robo_clipping_kindle.domain.command.Command;
import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import com.lionfish.robo_clipping_kindle.domain.request.ExportRequestDTO;
import com.lionfish.robo_clipping_kindle.domain.response.ExportResponseDTO;
import com.lionfish.robo_clipping_kindle.validator.CommandValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPooled;

import javax.websocket.server.PathParam;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ClippingController {

    private static final Logger logger = LoggerFactory.getLogger(ClippingController.class);
    private static final String DEFAULT_FILE_NAME_SEPARATOR = "-";

    ICommand responseCommand = CommandMapEnum.getCommandClass("response").getCommandClass();

    /**
     * Export clippings with especified command(eg: Notion, Markdown, Json, etc...)
     * @param command desired command to process the clippingFile
     * @param exportRequestDTO string containing all data from 'My Clippings'
     * @return byte[]
     */
    @PostMapping("/export")
    public Object export(@PathParam(value = "command") String command, @RequestBody ExportRequestDTO exportRequestDTO){
        logger.info("[Message] Export process initiated...");
        Command comm = CommandMapEnum.getCommandClass(command);
        ResponseData responseData;
        CommandValidator commandValidator = new CommandValidator(comm, CommandType.REQUEST);
        if(commandValidator.validate()){
            JedisPooled redis = RedisConnect.getRedisConnection();
            String[] tokenInfo = exportRequestDTO.getToken().split(DEFAULT_FILE_NAME_SEPARATOR);
            String currentIndex = tokenInfo[2];
            String currentToken = tokenInfo[0] + DEFAULT_FILE_NAME_SEPARATOR + tokenInfo[2];

            redis.set(currentToken, currentIndex);
            logger.info("[Redis] Token {{}} created.", currentToken);

            logger.info("[Message] Processing {{}} clippings", currentToken);

            ExportResponseDTO commandResponse = (ExportResponseDTO) comm.getCommandClass().execute(exportRequestDTO.getClippings());
            if(commandResponse != null && commandResponse.getResult() != null
                    && commandResponse.getClippingCount() > 0
                    && commandResponse.getBookCount() > 0){

                responseData = new ResponseData(ResponseMap.OK);
                responseData.setBody(commandResponse);
            }
            else{
                responseData = new ResponseData(ResponseMap.BAD_REQUEST);
            }
        }
        else{
            responseData = new ResponseData(ResponseMap.BAD_REQUEST);
            responseData.setBody("Invalid command " + command );
        }

        return responseCommand.execute(responseData);
    }
}
