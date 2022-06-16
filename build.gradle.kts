import xyz.unifycraft.gradle.tools.VersionType

plugins {
    kotlin("jvm") version("1.6.21")
    java
    id("gg.essential.loom") version("0.10.0.3")
    id("xyz.unifycraft.gradle.tools") version("1.5.1")
    id("xyz.unifycraft.gradle.tools.shadow") version("1.5.1")
    id("xyz.unifycraft.gradle.tools.releases") version("1.5.1")
    id("xyz.unifycraft.gradle.tools.blossom") version("1.5.1")
}

releases {
    modrinth {
        projectId.set("9TE1LEsK")
        versionType.set(VersionType.RELEASE)
        gameVersions.set(listOf("1.8.9"))
        loaders.set(listOf("forge"))
    }
}

unifycraft {
    useEssential()
}

loomHelper {
    useTweaker("gg.essential.loader.stage0.EssentialSetupTweaker")
}

repositories {
    mavenCentral()
}

dependencies {
    // Audio formatting/playing.
    unishade("com.googlecode.soundlibs:basicplayer:3.0.0.0")
}
