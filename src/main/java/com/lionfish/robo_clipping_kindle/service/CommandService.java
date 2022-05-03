package com.lionfish.robo_clipping_kindle.service;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.domain.response.ResponseData;
import com.lionfish.robo_clipping_kindle.domain.response.ResponseMap;
import com.lionfish.robo_clipping_kindle.domain.command.Command;
import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import com.lionfish.robo_clipping_kindle.validator.IValidator;
import com.lionfish.robo_clipping_kindle.validator.ValidatorMapEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandService {
    private static final Logger logger = LoggerFactory.getLogger(CommandService.class);
    private CommandService(){}

    public static ResponseData buildResponse(CommandType type, String command, Object request){
        ResponseData responseData;
        logger.info("[Message] Command: {{}}, CommandType: {{}}", command, type);
        if(type == null || CommandType.INTERNAL.equals(type)){
            responseData = new ResponseData(ResponseMap.BAD_REQUEST);
            responseData.setBody("Invalid path.");
            logger.error("[Error] Invalid command/path combination.");
            return responseData;
        }

        Command comm = CommandMapEnum.getCommandClass(type.getType() + "-" + command);
        IValidator commandValidator = ValidatorMapEnum.loadValidator(CommandType.COMMAND);
        if(commandValidator != null && commandValidator.validate(comm)){

            logger.info("[Message] Processing clippings");

            try {
                Object commandResponse = comm.getCommandClass().execute(request);
                IValidator validator = ValidatorMapEnum.loadValidator(type);
                if(validator != null && validator.validate(commandResponse)){
                    responseData = new ResponseData(ResponseMap.OK);
                    responseData.setBody(commandResponse);
                }
                else{
                    responseData = new ResponseData(ResponseMap.BAD_REQUEST);
                }
            } catch (Exception e) {
                logger.error("[Error] There's a error trying to execute command.", e);
                responseData = new ResponseData(ResponseMap.BAD_REQUEST);
            }
        }
        else{
            responseData = new ResponseData(ResponseMap.BAD_REQUEST);
            responseData.setBody("Invalid " + type.getType() + " type " + command );
        }
        return responseData;
    }
}
