package ru.entryset.api.database;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.entryset.api.util.function.SafeConsumer;
import ru.entryset.api.util.function.SafeFunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Общий класс для всех реализаций базы данных.
 *
 * @author EntrySet
 */
@NoArgsConstructor(
        access = AccessLevel.PROTECTED
)
public abstract class Database {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private HikariConfig config;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private HikariDataSource dataSource;

    /**
     * Инициализирует утилиту для базы данных.
     */
    public void start() {
        close();

        dataSource = new HikariDataSource(config);
    }

    /**
     * Завершает работу утилиты для базы данных.
     */
    public void close() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
        }
    }

    /**
     * Возвращает соединение из пула, которое необходимо закрывать.
     *
     * @return соединение
     * @throws SQLException возможное исключение
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Выполняет <strong>SELECT</strong> запрос, и преобразует {@link ResultSet} в необходимый формат.
     *
     * @param query    запрос
     * @param function функция для преобразования
     * @param <V>      нужный формат данных
     * @return результат в виде {@link CompletableFuture<V>}
     */
    public <V> CompletableFuture<V> select(String query, SafeFunction<ResultSet, V> function) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return execute(query, function, null);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Выполняет <strong>SELECT</strong> запрос с аргументами, и преобразует {@link ResultSet} в необходимый формат.
     *
     * @param query    запрос
     * @param function функция для преобразования
     * @param args     аргументы запроса
     * @param <V>      нужный формат данных
     * @return результат в виде {@link CompletableFuture<V>}
     */
    public <V> CompletableFuture<V> select(String query, SafeFunction<ResultSet, V> function, Object... args) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return execute(query, function, args);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Выполняет <strong>SELECT</strong> запрос с аргументами, и вызывает калбек с результатом.
     *
     * @param query    запрос
     * @param consumer калбек для обработки результата
     * @param args     аргументы запроса
     * @return результат в виде {@link CompletableFuture<Void>}
     */
    public CompletableFuture<Void> select(String query, SafeConsumer<ResultSet> consumer, Object... args) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return execute(query, rs -> {
                    consumer.accept(rs);
                    return null;
                }, args);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Выполняет <strong>SELECT</strong> запрос, и вызывает калбек с результатом.
     *
     * @param query    запрос
     * @param consumer калбек для обработки результата
     * @return результат в виде {@link CompletableFuture<Void>}
     */
    public CompletableFuture<Void> select(String query, SafeConsumer<ResultSet> consumer) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return execute(query, rs -> {
                    consumer.accept(rs);
                    return null;
                }, null);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Выполняет <strong>INSERT</strong>, <strong>UPDATE</strong>, или <strong>DELETE</strong> запрос.
     *
     * @param query запрос
     * @return результат в виде {@link CompletableFuture<Void>}
     */
    public CompletableFuture<Void> update(String query) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return execute(query, null, null);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Выполняет <strong>INSERT</strong>, <strong>UPDATE</strong>, или <strong>DELETE</strong> запрос с аргументами.
     *
     * @param query запрос
     * @param args  аргументы
     * @return результат в виде {@link CompletableFuture<Void>}
     */
    public CompletableFuture<Void> update(String query, Object... args) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return execute(query, null, args);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Выполняет все запросы из файла, если он существует.
     *
     * @param file файл
     * @return результат в виде {@link CompletableFuture<Void>}
     */
    public CompletableFuture<Void> execute(File file) {
        try {
            return execute(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * Выполняет все запросы из потока.
     *
     * @param stream поток
     * @return результат в виде {@link CompletableFuture<Void>}
     */
    public CompletableFuture<Void> execute(InputStream stream) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                try (Scanner scanner = new Scanner(stream).useDelimiter(";")) {
                    while (scanner.hasNext()) {
                        String query = scanner.next().trim();

                        if (!query.isEmpty())
                            execute(query, null, null);
                    }
                }
                return null;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Общий метод выполнения запроса,<br>
     * Смотреть код можно только с закрытыми глазами.
     *
     * @param query    запрос
     * @param function калбек
     * @param args     аргументы
     * @param <V>      новый тип
     * @return результат в виде {@link CompletableFuture<V>}
     * @throws Exception возможная ошибка
     */
    protected <V> V execute(String query, SafeFunction<ResultSet, V> function, Object[] args) throws Exception {
        try (Connection connection = getConnection();

             Statement statement = args == null ? connection.createStatement() : connection.prepareStatement(query)) {


            boolean result;
            if (args == null) {
                result = statement.execute(query);
            } else {
                PreparedStatement prepared = (PreparedStatement) statement;
                for (int i = 0; i < args.length; i++)
                    prepared.setObject(1 + i, args[i]);

                result = prepared.execute();
            }

            if (result && function != null)
                try (ResultSet rs = statement.getResultSet()) {
                    return function.apply(rs);
                }
        } catch (Exception e) {
            // https://stackoverflow.com/questions/12726665/java-sql-sqlexception-lock-wait-timeout-exceeded-try-restarting-transaction-ex
            String message = e.getMessage();
            if (message != null && message.contains("try restarting transaction")) {
                new RuntimeException("Query '" + query + "' failed! Restarting", e).printStackTrace();

                Thread.sleep(100);
                return execute(query, function, args);
            } else
                throw new RuntimeException("Query '" + query + "' is failed", e);
        }
        return null;
    }

}
