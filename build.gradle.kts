plugins {
    kotlin("jvm") version "2.3.20"
    id("io.ktor.plugin") version "3.6.0"
    application
}

group = "org.emulator"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-engine:6.0.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.3.20")
}

application {
    mainClass.set("org.emulator.MainKt")
}

ktor {
    fatJar {
        archiveFileName.set("shellemulator.jar")
    }
}

