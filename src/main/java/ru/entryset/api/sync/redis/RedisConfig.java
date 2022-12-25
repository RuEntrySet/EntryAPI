package ru.entryset.api.sync.redis;

public interface RedisConfig {

    /**
     * Возвращает адрес запущенной БД
     *
     * @return адрес
     */
    String getAddress();

    /**
     * Возвращает порт запущенной БД
     *
     * @return порт
     */
    int getPort();

    /**
     * Возвращает пароль пользователя
     *
     * @return пароль пользователя
     */
    String getPassword();

}
