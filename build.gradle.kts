plugins {
    java
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version ("6.1.0")
}

group = "ru.entryset"
version = "1.0.0"

publishing {
    repositories {
        maven("https://maven.pkg.github.com/RuEntrySet/maven") {
            name = "GitHubPackages"
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.22")

    annotationProcessor("org.projectlombok:lombok:1.18.22")

    implementation("com.zaxxer:HikariCP:4.0.3")
}

tasks.withType<JavaCompile> {
    options.encoding = Charsets.UTF_8.name()
    options.release.set(8)
}

tasks.withType<Javadoc> {
    options.encoding = Charsets.UTF_8.name()
}

tasks.withType<ProcessResources> {
    filteringCharset = Charsets.UTF_8.name()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}