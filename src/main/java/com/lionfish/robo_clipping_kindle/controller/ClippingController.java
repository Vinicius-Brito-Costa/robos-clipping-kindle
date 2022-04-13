package com.lionfish.robo_clipping_kindle.controller;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.command.ICommand;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseMap;
import com.lionfish.robo_clipping_kindle.database.redis.RedisConnect;
import com.lionfish.robo_clipping_kindle.domain.File.ClippingFile;
import com.lionfish.robo_clipping_kindle.service.FileService;
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
        ResponseMap responseMap = ResponseMap.CONTINUE;
        JedisPooled redis = RedisConnect.getRedisConnection();
        String[] tokenInfo = clippingFile.getToken().split(DEFAULT_FILE_NAME_SEPARATOR);
        int finalIndex = Integer.parseInt(tokenInfo[1]);
        String currentIndex = tokenInfo[2];
        String currentToken = tokenInfo[0] + DEFAULT_FILE_NAME_SEPARATOR + tokenInfo[2];
        String fName = currentToken + DEFAULT_FILE_EXT;

        redis.set(currentToken, currentIndex);
        logger.info("[Redis] Token {{}} created.", currentToken);
        FileService.createFile(fName, clippingFile.getClippings());

        logger.info("[Message] Processing {{}} clippings", currentToken);
        if(redis.keys(tokenInfo[0] + DEFAULT_FILE_NAME_SEPARATOR + SELECTOR_ALL).size() == finalIndex){
            StringBuilder res = new StringBuilder();
            for(int index = 0; index < finalIndex; index++){
                String redisToken = tokenInfo[0] + DEFAULT_FILE_NAME_SEPARATOR + index;
                String fileName = redisToken + DEFAULT_FILE_EXT;
                res.append(new String(FileService.getFileAsBytes(fileName)));
                redis.del(redisToken);
                logger.info("[Redis] Token {{}} deleted", redisToken);
            }
            if(comm != null){
                HashMap<Object, Object> commandResponse = (HashMap<Object, Object>) comm.execute(res.toString());
                if(commandResponse != null && commandResponse.size() > 0){
                    responseMap = ResponseMap.OK;
                    responseMap.setResponseDataBody(commandResponse);
                }
                else{
                    responseMap = ResponseMap.BAD_REQUEST;
                }
            }
            else{
                responseMap = ResponseMap.BAD_REQUEST;
                responseMap.setResponseDataBody("Invalid command " + command );
            }
            for(int index = 0; index < finalIndex; index++){
                String fileName = tokenInfo[0] + DEFAULT_FILE_NAME_SEPARATOR + index + DEFAULT_FILE_EXT;
                FileService.deleteFile(fileName);
            }
        }
        return responseCommand.execute(responseMap);
    }
}
