# Утилита для работы с базой данных

Для примера будет использована реализация `MysqlDatabase`.

**Важно!** Все действия с утилитой возвращают `CompletableFuture`,
по этому вы должны знать как он работает. Полезные ссылки:
[Java Docs](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/CompletableFuture.html),
[Старое руководство на Русском](https://annimon.com/article/3462).

**Важно!** Необходимо самому обрабатывать ошибки, если метод этого не подразумивает.

### Импорт утилит
```java
import ru.entryset.api.database.Database;
import ru.entryset.api.database.mysql.MysqlDatabase;
```

### Создание и запуск утилиты

```java
// Создаём новый инстанс утилиты
Database database = new MysqlDatabase(address, username, password, database);

// Запускаем утилиту
database.start();

// Для завершения необходимо вызывать метод close
database.close();
```

## Таблица и данные

Таблица для тестов:
```sql
CREATE TABLE `test` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COLLATE=utf8mb4_unicode_ci;
```

Данные для тестов:
```sql
INSERT INTO `test` (`name`) VALUES
('Abraham'),
('William'),
('Mathew');
```

## Примеры SELECT запросов

### Задача: записать все имена в строку через запятую

#### Решение с преобразованием

```java
String result = database.select("SELECT `name` FROM `test`", rs -> {
    StringBuilder builder = new StringBuilder();

    // Собираем пришедший результат
    while (rs.next()) {
        // Если уже есть данные, то добавляем запятую
        if (!builder.isEmpty())
            builder.append(", ");

        // Добавляем имя
        builder.append(rs.getString("name"));
    }

    // Возвращаем новый результат
    return builder.toString();
}).join();

System.out.println(result);
```

#### Решение без преобразования

```java
List<String> result = new ArrayList<>();

database.select("SELECT `name` FROM `test`", rs -> {
    // Добавляем результат выполнения запроса в коллекцию
    while (rs.next())
        // Так как result у нас final, то мы можем
        // использовать его в лямбде (естественно
        // только в одном потоке)
        result.add(rs.getString("name"));
})
// После выполнения форматируем и выводим
.thenRun(() -> {
    String formatted = String.join(", ", result);
    System.out.println(formatted);
});
```

#### Результат:

```text
Abraham, William, Mathew
```

### Задача: вывести все имена, которые содержат букву `w`

#### Решение с аргументами

```java
database.select("SELECT `name` FROM `test` WHERE `name` LIKE ?", rs -> {
    // Выводим весь пришедший результат
    while (rs.next())
        System.out.println(rs.getString("name"));
}, "%w%");
```

#### Результат:

```text
William
Mathew
```

### Задача: вернуть количество имён, содержащих в себе букву `h`

#### Решение с преобразованием и аргументами

```java
int result = database.select("SELECT COUNT(*) FROM `test` WHERE `name` LIKE ?", rs -> rs.getInt(1), "%h%").join();

// Выводим результат
System.out.println(result);
```

#### Результат:

```text
2
```