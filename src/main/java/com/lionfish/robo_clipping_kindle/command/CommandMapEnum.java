package com.lionfish.robo_clipping_kindle.command;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/***
 * Responsible for holding and returning all commands.
 */
public enum CommandMapEnum {
    RESPONSE("response", "ResponseCommand"),
    DOWNLOAD("download", "DownloadCommand"),
    DOWNLOAD_DOCX("download-docx", "DownloadDocxCommand"),
    NOTION("notion", "NotionCommand");

    private static HashMap<String, String> commandMap;
    private static final String FQN = "com.lionfish.robo_clipping_kindle.command.";

    CommandMapEnum(String commandName, String commandClass){
        addCommand(commandName, FQN + commandClass);
    }

    private static void addCommand(String comandName, String comandClass){
        if(commandMap == null){
            commandMap = new HashMap<>();
        }
        commandMap.put(comandName, comandClass);
    }

    /***
     * Returns a command based on the provided string
     * @param command desired command
     * @return the selected command
     */
    public static ICommand getCommandClass(String command){
        String returnedClass = commandMap.get(command);
        if(returnedClass == null){
            return null;
        }
        try {
            return (ICommand) ICommand.class.getClassLoader().loadClass(returnedClass).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            return null;
        }
    }
}
