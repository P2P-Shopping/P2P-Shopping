import com.github.gradle.node.npm.task.NpmTask

plugins {
    java
    id("org.springframework.boot") version "4.0.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.sonarqube") version "7.2.3.7755"
    id("com.github.node-gradle.node") version "7.0.2"
}

group = "p2p-shopping"
version = "0.0.1-SNAPSHOT"
description = "P2P-Shopping"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    runtimeOnly("org.postgresql:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

sonar {
    properties {
        property("sonar.projectKey", "P2P-Shopping_P2P-Shopping")
        property("sonar.organization", "p2p-shopping")
    }
}

val nodeInstalled = try {
    ProcessBuilder("node", "-v").start().waitFor() == 0
} catch (e: Exception) {
    false
}

node {
    download.set(!nodeInstalled)
    if (!nodeInstalled) {
        version.set("20.11.0")
    }

    nodeProjectDir.set(file("frontend"))
}

val buildFrontend = tasks.register<NpmTask>("buildFrontend") {
    dependsOn("npmInstall")
    args.set(listOf("run", "build"))
}

// Ensure the frontend is built before processing resources
tasks.processResources {
    dependsOn(buildFrontend)
    from(file("frontend/dist")) {
        into("static")
    }
}