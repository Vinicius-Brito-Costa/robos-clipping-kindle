package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.domain.command.Command;
import com.lionfish.robo_clipping_kindle.domain.command.CommandType;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/***
 * Responsible for holding and returning all commands.
 */
public enum CommandMapEnum {
    RESPONSE("response", "ResponseCommand", CommandType.INTERNAL),
    DOWNLOAD_JSON("download-json", "DownloadJSONCommand", CommandType.REQUEST),
    DOWNLOAD_DOCX("download-docx", "DownloadDocxCommand", CommandType.REQUEST),
    NOTION("notion", "NotionCommand", CommandType.REQUEST);

    private static HashMap<String, Command> commandMap;
    private static final String FQN = "com.lionfish.robo_clipping_kindle.command.";

    CommandMapEnum(String commandName, String commandClass, CommandType type){
        addCommand(commandName, FQN + commandClass, type);
    }

    private static void addCommand(String commandName, String commandClass, CommandType type){
        if(commandMap == null){
            commandMap = new HashMap<>();
        }
        ICommand newCommand = loadCommand(commandClass);
        if(newCommand != null){
            commandMap.put(commandName, new Command(newCommand, type));
        }
    }

    /***
     * Returns a command based on the provided string
     * @param command desired command
     * @return the selected command
     */
    public static Command getCommandClass(String command){
        return commandMap.get(command);
    }

    private static ICommand loadCommand(String command){
        try {
            return (ICommand) ICommand.class.getClassLoader().loadClass(command).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
