# Entry API

### Реализованные утилиты
* [Утилита для работы с базой данных](src/main/java/ru/hyperspacemc/api/database/README.md)

### Подключение библиотеки
#### build.gradle
```groovy
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/RuEntrySet/EntryAPI")
        credentials {
            username = "RuEntrySet"
            password = "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"
        }
    }
}

dependencies {
    implementation("ru.entryset:api:1.0.0")
}
```