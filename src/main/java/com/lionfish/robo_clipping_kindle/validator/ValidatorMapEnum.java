package com.lionfish.robo_clipping_kindle.validator;

import com.lionfish.robo_clipping_kindle.domain.command.CommandType;

import java.util.HashMap;
import java.util.Map;

public enum ValidatorMapEnum {
    COMMAND("CommandValidator", CommandType.COMMAND),
    DOWNLOAD_RESPONSE("DownloadResponseValidator", CommandType.DOWNLOAD),
    EXPORT_RESPONSE("IntegrationResponseValidator", CommandType.EXPORT);

    private static HashMap<CommandType, String> validatorMap;
    private static final String FQN = "com.lionfish.robo_clipping_kindle.validator.";

    ValidatorMapEnum(String validatorClass, CommandType type){
        addCommand(FQN + validatorClass, type);
    }

    private static void addCommand(String validatorClass, CommandType type){
        if(validatorMap == null){
            validatorMap = new HashMap<>();
        }
        validatorMap.put(type, validatorClass);
    }

    /***
     * Returns a validator based on the provided string
     * @param type desired validator
     * @return the selected validator
     */
    public static IValidator loadValidator(CommandType type){
        for(Map.Entry<CommandType, String> entry : validatorMap.entrySet()){
            if(type.equals(entry.getKey())){
                try {
                    return (IValidator) Class.forName(entry.getValue()).getDeclaredConstructor().newInstance();
                } catch (Exception e){
                    return null;
                }
            }
        }
        return null;
    }
}
