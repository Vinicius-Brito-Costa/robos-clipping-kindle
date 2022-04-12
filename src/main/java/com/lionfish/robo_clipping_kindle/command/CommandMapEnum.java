package com.lionfish.robo_clipping_kindle.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public enum CommandMapEnum {
    Response("response", "ResponseCommand"),
    Download("download", "DownloadCommand"),
    Notion("notion", "NotionCommand");

    private final static Logger logger = LoggerFactory.getLogger(CommandMapEnum.class);
    private static HashMap<String, String> commandMap;
    private final static String FQN = "com.lionfish.robo_clipping_kindle.command.";

    CommandMapEnum(String comandName, String comandClass){
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
            logger.info("[Message] Command {{}} loaded.", command);
            return comm;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            return null;
        }
    }
}
