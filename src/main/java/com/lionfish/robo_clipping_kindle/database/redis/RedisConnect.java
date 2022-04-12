package com.lionfish.robo_clipping_kindle.database.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPooled;

public class RedisConnect {

    private static final Logger logger = LoggerFactory.getLogger(RedisConnect.class);
    private static RedisConnect instance;
    private final JedisPooled redisConnection;

    private RedisConnect(){
        String host = "localhost";
        int port = 6379;
        this.redisConnection = new JedisPooled(host, port);
        logger.info("[Message] Redis connection instance created. host {{}}, port {{}}", host, port);
    }

    public static JedisPooled getRedisConnection(){
        instanceKeeper();
        return instance.redisConnection;
    }
    private static void instanceKeeper(){
        if(instance == null){
            instance = new RedisConnect();
        }
    }
}
