package com.lionfish.robo_clipping_kindle.validator;

import com.lionfish.robo_clipping_kindle.domain.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandValidator implements IValidator{

    private static final Logger logger = LoggerFactory.getLogger(CommandValidator.class);

    @Override
    public boolean validate(Object validatedValue){
        boolean commandTypeNotNull;
        Command command = (Command) validatedValue;
        try{
            commandTypeNotNull = command.getCommandType() != null;
        } catch (NullPointerException e){
            return false;
        }
        boolean commandClassIsNotNull = command.getCommandClass() != null;
        boolean valid = commandTypeNotNull && commandClassIsNotNull;
        logger.info("[Message] Is command valid? {}", valid);
        return valid;
    }
}
