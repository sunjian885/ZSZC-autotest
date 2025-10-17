package com.common.utils;

import redis.clients.jedis.Jedis;

public class RedisUtil {
    // Redis连接信息（请替换为实际配置）
    private static final String REDIS_HOST = "192.168.4.138";
    private static final int REDIS_PORT = 6379;
    private static final String REDIS_USERNAME = "default";
    private static final String REDIS_PASSWORD = "Jsc#6379";

    /**
     * 向Redis设置键值对
     * @param key 键
     * @param value 值
     * @return 操作结果（OK表示成功）
     */
    public static String setValue(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedisConnection();
            return jedis.set(key, value);
        } finally {
            closeConnection(jedis);
        }
    }

    /**
     * 从Redis查询指定键的值
     * @param key 键
     * @return 键对应的value，若不存在则返回null
     */
    public static String getValue(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisConnection();
            return jedis.get(key);
        } finally {
            closeConnection(jedis);
        }
    }

    /**
     * 从Redis删除指定键
     * @param key 键
     * @return 1表示删除成功，0表示键不存在
     */
    public static Long deleteKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedisConnection();
            return jedis.del(key);
        } finally {
            closeConnection(jedis);
        }
    }

    /**
     * 获取Redis连接并进行认证
     * @return 已认证的Jedis实例
     */
    private static Jedis getJedisConnection() {
        Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        // 根据Redis版本选择认证方式（6.0+支持用户名认证）
        jedis.auth(REDIS_USERNAME, REDIS_PASSWORD);
        // 若Redis版本低于6.0，使用以下认证方式
        // jedis.auth(REDIS_PASSWORD);
        return jedis;
    }

    /**
     * 关闭Redis连接
     * @param jedis Jedis实例
     */
    private static void closeConnection(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (Exception e) {
                System.err.println("关闭Redis连接失败: " + e.getMessage());
            }
        }
    }

    // 测试示例
    public static void main(String[] args) {
        String testKey = "CONTRACT_EXPIRED_TIME_REDIS_KEY_1928372313123094530";

        // 测试设置操作
//        String setResult = setValue(testKey, "2024-12-31");
//        System.out.println("设置结果: " + setResult); // 预期输出OK

        // 测试查询操作
        String value = getValue(testKey);
        System.out.println("查询结果: " + value); // 预期输出设置的值

        // 测试删除操作
//        Long deleteResult = deleteKey(testKey);
//        System.out.println("删除结果（1表示成功）: " + deleteResult);
    }
}
