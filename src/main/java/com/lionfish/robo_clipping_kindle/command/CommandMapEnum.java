package com.lionfish.robo_clipping_kindle.command;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public enum CommandMapEnum {
    Download("download", "DownloadCommand"),
    Notion("notion", "NotionCommand");

    private static HashMap<String, String> commandMap;
    private final static String FQN = "com.lionfish.robo_clipping_kindle.command.";

    private CommandMapEnum(String comandName, String comandClass){
        addCommand(comandName, FQN + comandClass);
    }

    private static void addCommand(String comandName, String comandClass){
        if(commandMap == null){
            commandMap = new HashMap<>();
        }
        commandMap.put(comandName, comandClass);
    }
    public static ICommand getCommandClass(String command){
        String returnedClass = commandMap.get(command);
        if(returnedClass == null){
            return null;
        }
        try {
            ICommand comm = (ICommand) ICommand.class.getClassLoader().loadClass(returnedClass).getDeclaredConstructor().newInstance();
            return comm;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            return null;
        }
    }
}
