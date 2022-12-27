plugins {
    id("java")
    id("maven-publish")
}

group = "ru.entryset"
version = "3.2.1"

repositories {
    mavenCentral()
    /*spigot repository*/
    maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    /*PlaceholderAPI repository*/
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

publishing {

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/RuEntrySet/EntryAPI")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_PASSWORD")
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

    // spigot dependence
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    // redis
    implementation("redis.clients:jedis:4.2.0")
    // PlaceholderAPI
    compileOnly("me.clip:placeholderapi:2.10.9")

    implementation("com.zaxxer:HikariCP:4.0.3")

    compileOnly("org.projectlombok:lombok:1.18.22")

    annotationProcessor("org.projectlombok:lombok:1.18.22")


}