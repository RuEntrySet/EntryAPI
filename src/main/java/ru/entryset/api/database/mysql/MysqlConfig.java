package ru.entryset.api.database.mysql;

public interface MysqlConfig {

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
     * Возвращает имя пользователя
     *
     * @return имя пользователя
     */
    String getUsername();

    /**
     * Возвращает пароль пользователя
     *
     * @return пароль пользователя
     */
    String getPassword();

    /**
     * Возвращает базу данных, которую необходимо использовать
     *
     * @return база данных
     */
    String getDatabase();

}
