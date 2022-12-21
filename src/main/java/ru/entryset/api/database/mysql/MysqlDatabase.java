package ru.entryset.api.database.mysql;

import com.zaxxer.hikari.HikariConfig;
import ru.entryset.api.database.Database;

/**
 * Реализация базы данных для MySQL.<br>
 * <strong>Драйвер необходимо устанавливать самому!</strong>
 *
 * @author EntrySet
 */
public class MysqlDatabase extends Database {

    /**
     * Создаёт настроенную к работе утилиту с помощью конфига.
     *
     * @param config конфиг
     */
    public MysqlDatabase(MysqlConfig config) {
        this(config.getAddress(), config.getPort(), config.getUsername(), config.getPassword(), config.getDatabase());
    }

    /**
     * Создаёт настроенную к работе утилиту.
     *
     * @param address  адрес сервера
     * @param username имя пользователя
     * @param password пароль пользователя
     * @param database база данных
     */
    public MysqlDatabase(String address, String username, String password, String database) {
        this(address, 3306, username, password, database);
    }

    /**
     * Создаёт настроенную к работе утилиту.
     *
     * @param address  адрес сервера
     * @param port     порт сервера
     * @param username имя пользователя
     * @param password пароль пользователя
     * @param database база данных
     */
    public MysqlDatabase(String address, int port, String username, String password, String database) {
        this(String.format("jdbc:mysql://%s:%s/%s", address, port, database), username, password);
    }

    /**
     * Создаёт настроенную к работе утилиту.
     *
     * @param url      JDBC URL
     * @param username имя пользователя
     * @param password пароль пользователя
     */
    public MysqlDatabase(String url, String username, String password) {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        // Количество соединений не может превышать 5-ти
        config.setMaximumPoolSize(5);

        // Если соединение не отвечает более 5 секунд, то
        // оно считается умершим
        config.setConnectionTimeout(5000);

        // Если соединения не возвращается в пул более
        // 2-ух секунд, то триггерится leak detector
        config.setLeakDetectionThreshold(2000);

        // Ускоряем компиляцию одних и тех же prepared
        // запросов с помощью небольшого кеша
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "50");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "512");

        // Про доступные параметры можно прочитать здесь:
        // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-configuration-properties.html

        setConfig(config);
    }

}
