# Entry API

### Реализованные утилиты
* [Утилита для работы с базой данных](src/main/java/ru/entryset/api/database/README.md)

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