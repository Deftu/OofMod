import xyz.unifycraft.gradle.tools.VersionType

plugins {
    kotlin("jvm") version("1.6.21")
    id("gg.essential.loom") version("0.10.0.3")
    id("xyz.unifycraft.gradle.tools") version("1.2.4")
    id("xyz.unifycraft.gradle.tools.shadow") version("1.2.4")
    id("xyz.unifycraft.gradle.tools.releases") version("1.2.4")
    id("xyz.unifycraft.gradle.tools.blossom") version("1.2.4")
    java
}

releases {
    modrinth {
        projectId.set("9TE1LEsK")
        versionType.set(VersionType.RELEASE)
        gameVersions.set(listOf("1.8.9"))
        loaders.set(listOf("forge"))
    }
}

loomHelper {
    useTweaker("gg.essential.loader.stage0.EssentialSetupTweaker")
}

repositories {
    mavenCentral()

    maven("https://jitpack.io/")
    maven("https://repo.sk1er.club/repository/maven-public/")
    maven("https://repo.spongepowered.org/maven/")

    mavenLocal()
}

dependencies {
    // Audio formatting/playing.
    unishade("com.googlecode.soundlibs:tritonus-share:0.3.7.4")
    unishade("com.googlecode.soundlibs:mp3spi:1.9.5.4")
    unishade("com.googlecode.soundlibs:vorbisspi:1.0.3.3")

    // The Essential mod.
    unishade("gg.essential:loader-launchwrapper:1.1.3")
    compileOnly("gg.essential:essential-1.8.9-forge:2163")
}
