package com.lionfish.robo_clipping_kindle.validator;

import com.lionfish.robo_clipping_kindle.domain.command.Command;
import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandValidator implements IValidator{

    private static final Logger logger = LoggerFactory.getLogger(CommandValidator.class);
    private Command command;
    private CommandType requiredType;

    public CommandValidator(Command command, CommandType commandType){
        this.command = command;
        this.requiredType = commandType;
    }

    @Override
    public boolean validate(){
        boolean commandTypeNotNull;
        boolean isRequiredCommandType;
        try{
            commandTypeNotNull = command.getCommandType() != null;
            isRequiredCommandType = requiredType.equals(command.getCommandType());
        } catch (NullPointerException e){
            return false;
        }
        boolean commandClassIsNotNull = command.getCommandClass() != null;
        boolean valid = commandTypeNotNull && isRequiredCommandType && commandClassIsNotNull;
        logger.info("[ Message ] Is command valid? {}", valid);
        return valid;
    }
}
