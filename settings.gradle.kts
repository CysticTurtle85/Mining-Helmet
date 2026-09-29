pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        maven("https://maven.neoforged.net/releases") { name = "NeoForged" }
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    // Downloads the JDK a target needs (e.g. Java 25 for 26.x) when it isn't installed.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "mininghelmet"

// Every folder in targets/ is one Minecraft version + loader build, e.g. targets/1.21.1-neoforge.
file("targets").listFiles { f -> f.resolve("gradle.properties").isFile }!!.sorted().forEach {
    include(it.name)
    project(":${it.name}").projectDir = it
}
