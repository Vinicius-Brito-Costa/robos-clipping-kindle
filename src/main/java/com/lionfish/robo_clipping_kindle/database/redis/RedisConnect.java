package com.lionfish.robo_clipping_kindle.database.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPooled;

/**
 * Holds info for creating/using a singleton connection of Jedis
 */
public class RedisConnect {

    private static final Logger logger = LoggerFactory.getLogger(RedisConnect.class);
    private static RedisConnect instance;
    private final JedisPooled redisConnection;

    private RedisConnect(){
        this("localhost", 6379);
    }

    private RedisConnect(String host, int port){
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
