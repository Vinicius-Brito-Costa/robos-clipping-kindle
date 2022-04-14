package com.lionfish.robo_clipping_kindle.database.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisPooled;

public class RedisConnectTest {

    @Test
    void sameRedisInstance(){
        JedisPooled instanceOne = RedisConnect.getRedisConnection();
        JedisPooled instanceTwo = RedisConnect.getRedisConnection();

        Assertions.assertEquals(instanceOne, instanceTwo);
    }
}
