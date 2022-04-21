package com.lionfish.robo_clipping_kindle.controller;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.command.ICommand;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseData;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseMap;
import com.lionfish.robo_clipping_kindle.database.redis.RedisConnect;
import com.lionfish.robo_clipping_kindle.domain.file.ClippingFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPooled;

import javax.websocket.server.PathParam;
import java.util.HashMap;


@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ClippingController {

    private static final Logger logger = LoggerFactory.getLogger(ClippingController.class);
    private static final String DEFAULT_FILE_NAME_SEPARATOR = "-";
    private static final String DEFAULT_FILE_EXT = ".txt";
    private static final String SELECTOR_ALL = "*";
    ICommand responseCommand = CommandMapEnum.getCommandClass("response");

    /**
     * Export clippings with especified command(eg: Notion, Markdown, Json, etc...)
     * @param command desired command to process the clippingFile
     * @param clippingFile string containing all data from 'My Clippings'
     * @return byte[]
     */
    @PostMapping("/exportMulti")
    @SuppressWarnings("unchecked")
    public Object exportClippingsMulti(@PathParam(value = "command") String command, @RequestBody ClippingFile clippingFile){
        logger.info("[Message] Export process initiated...");
        ICommand comm = CommandMapEnum.getCommandClass(command);
        ResponseData responseData;
        JedisPooled redis = RedisConnect.getRedisConnection();
        String[] tokenInfo = clippingFile.getToken().split(DEFAULT_FILE_NAME_SEPARATOR);
        String currentIndex = tokenInfo[2];
        String currentToken = tokenInfo[0] + DEFAULT_FILE_NAME_SEPARATOR + tokenInfo[2];

        redis.set(currentToken, currentIndex);
        logger.info("[Redis] Token {{}} created.", currentToken);

        logger.info("[Message] Processing {{}} clippings", currentToken);
        if(comm != null){
            HashMap<Object, Object> commandResponse = (HashMap<Object, Object>) comm.execute(clippingFile.getClippings());
            if(commandResponse != null && commandResponse.size() > 0){
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
