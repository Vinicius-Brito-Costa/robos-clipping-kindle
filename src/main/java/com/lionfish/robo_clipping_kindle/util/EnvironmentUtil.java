package com.lionfish.robo_clipping_kindle.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentUtil {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentUtil.class);

    private EnvironmentUtil(){}

    /***
     * Return an environment variable
     * @param env environment variable name
     * @return the desired environment variable or null
     */
    public static String getEnvVariable(String env){
        String response = System.getenv().get(env);
        if(response == null){
            logger.error("[ Error ] Environment variable {{}} is null", env);
        }
        return response;
    }
}
