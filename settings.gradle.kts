import groovy.lang.MissingPropertyException

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()

        maven("https://jitpack.io/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.fabricmc.net")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.sk1er.club/repository/maven-public/")
        maven("https://server.bbkr.space/artifactory/libs-release/")

        mavenLocal()
    }
}

val projectName: String = extra["mod.name"]?.toString()
    ?: throw MissingPropertyException("mod.name has not been set.")
rootProject.name = projectName
