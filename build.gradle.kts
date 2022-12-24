plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version ("6.1.0")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

publishing {

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/RuEntrySet/EntryAPI")
            credentials {
                username = (project.findProperty("gpr.user") ?: System.getProperty("USERNAME")).toString()
                password = (project.findProperty("gpr.password") ?: System.getProperty("PASSWORD")).toString()
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

dependencies {

    implementation("com.zaxxer:HikariCP:4.0.3")

    compileOnly("org.projectlombok:lombok:1.18.22")

    annotationProcessor("org.projectlombok:lombok:1.18.22")


}