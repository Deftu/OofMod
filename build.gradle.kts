import xyz.unifycraft.gradle.utils.GameSide

plugins {
    java
    kotlin("jvm") version("1.6.10")
    val ucgt = "1.11.1"
    id("xyz.unifycraft.gradle.tools") version(ucgt)
    id("xyz.unifycraft.gradle.tools.loom") version(ucgt)
    id("xyz.unifycraft.gradle.tools.blossom") version(ucgt)
    id("xyz.unifycraft.gradle.tools.shadow") version(ucgt)
}

unifycraft {
    useEssential()
    useDevAuth()
}

loomHelper {
    useArgument("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker", GameSide.CLIENT)
}
