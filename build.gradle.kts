plugins {
    id("java")
    id("maven-publish")
}

group = "ru.entryset"
version = "1.0.0"

repositories {
    mavenCentral()
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

    implementation("com.zaxxer:HikariCP:4.0.3")

    compileOnly("org.projectlombok:lombok:1.18.22")

    annotationProcessor("org.projectlombok:lombok:1.18.22")


}