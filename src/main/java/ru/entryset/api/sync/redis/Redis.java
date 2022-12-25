package ru.entryset.api.sync.redis;

import redis.clients.jedis.Jedis;

/**
 * Реализация шнура для Redis.<br>
 * <strong>Драйвер необходимо устанавливать самому!</strong>
 *
 * @author EntrySet
 */
public class Redis implements RedisMap {

    private String address;

    private int port = 3306;

    private String password;

    private Jedis jedis;

    /**
     * Создаёт настроенную к работе утилиту с помощью конфига.
     *
     * @param config конфиг
     */
    public Redis(RedisConfig config) {
        connect(config.getAddress(), config.getPort(), config.getPassword());
    }

    /**
     * Создаёт настроенную к работе утилиту.
     *
     * @param address  адрес сервера
     * @param password пароль
     */
    public Redis(String address, String password) {
        connect(address, getPort(), password);
    }

    /**
     * Создаёт настроенную к работе утилиту.
     *
     * @param address  адрес сервера
     * @param port     порт
     * @param password пароль
     */
    public Redis(String address, int port, String password) {
        connect(address, port, password);
    }

    private void connect(String address, int port, String password){
        this.address = address;
        this.port = port;
        this.password = password;
        try {
            this.jedis = new Jedis(getAddress(), getPort());
            getJedis().auth(getPassword());
        } catch (Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    public Jedis getJedis() {
        return jedis;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public void push(String source, String msg) {
        getJedis().publish(source, msg);
    }

    @Override
    public void push(byte[] source, byte[] msg) {
        getJedis().publish(source, msg);
    }

    @Override
    public void set(String key, String value) {
        getJedis().set(key, value);
    }

    @Override
    public void set(byte[] key, byte[] value) {
        getJedis().set(key, value);
    }

    @Override
    public String get(String key) {
        return getJedis().get(key);
    }

    @Override
    public byte[] get(byte[] key) {
        return getJedis().get(key);
    }
}
