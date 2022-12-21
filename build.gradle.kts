plugins {
    java
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version ("6.1.0")
}

group = "ru.entryset"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.22")

    annotationProcessor("org.projectlombok:lombok:1.18.22")

    implementation("com.zaxxer:HikariCP:4.0.3")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<JavaCompile> {
    options.encoding = Charsets.UTF_8.name()
}

tasks.withType<Javadoc> {
    options.encoding = Charsets.UTF_8.name()
}

tasks.withType<ProcessResources> {
    filteringCharset = Charsets.UTF_8.name()
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}