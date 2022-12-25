package ru.entryset.api.sync.redis;

public interface RedisMap {

    void push(String source, String msg);

    void push(byte[] source, byte[] msg);

    void set(String key, String value);

    void set(byte[] key, byte[] value);

    String get(String key);

    byte[] get(byte[] key);

}
