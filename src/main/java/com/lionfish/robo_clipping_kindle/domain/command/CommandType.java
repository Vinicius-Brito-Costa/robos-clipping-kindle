package com.lionfish.robo_clipping_kindle.domain.command;

import lombok.Getter;

@Getter
public enum CommandType {
    COMMAND("command"),
    INTERNAL("internal"),
    EXPORT("export"),
    INTEGRATION("integration"),
    DOWNLOAD("download");

    String type;
    CommandType(String type){
        this.type = type;
    }

    public static CommandType getCommandType(String commandType){
        try{
            return CommandType.valueOf(commandType.toUpperCase());
        }
        catch (IllegalArgumentException e){
            return null;
        }
    }
}
