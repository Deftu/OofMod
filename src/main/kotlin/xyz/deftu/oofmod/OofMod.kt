package xyz.deftu.oofmod

import gg.essential.api.EssentialAPI
import gg.essential.universal.ChatColor
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import xyz.deftu.oofmod.config.OofModConfig
import xyz.deftu.oofmod.utils.PlayerHelper
import java.io.File

@Mod(
    name = OofMod.NAME,
    version = OofMod.VERSION,
    modid = OofMod.ID
) class OofMod {
    @Mod.EventHandler
    fun initialize(event: FMLInitializationEvent) {
        configDir = File(File(File(Minecraft.getMinecraft().mcDataDir, "config"), "Deftu"), NAME)
        soundsDir = File(configDir, "Sounds")
        if (!configDir.exists() && !configDir.mkdirs())
            throw IllegalStateException("Failed to make $NAME config directory.")
        if (!soundsDir.exists() && !soundsDir.mkdirs())
            throw IllegalStateException("Failed to make $NAME sounds directory.")

        // Start the player utility checker.
        PlayerHelper.initialize()
        // Initialize the mod config via Vigilance's in-built initializer method.
        OofModConfig.initialize()

        // Register our command with Essential's command registry.
        OofModCommand.register()

        // Register our listeners.
        MinecraftForge.EVENT_BUS.register(OofModListener)
    }

    companion object {
        const val ID = "@MOD_ID@"
        const val VERSION = "@MOD_VERSION@"
        const val NAME = "@MOD_NAME@"
        const val DEFAULT_SOUND_URL = "https://oofmodsound.powns.dev/oof.wav"
        const val DISCORD_URL = "https://shr.deftu.xyz/discord"

        lateinit var configDir: File
            private set
        lateinit var soundsDir: File
            private set

        @JvmStatic
        fun sendMessage(message: String) = EssentialAPI.getMinecraftUtil().sendMessage(
            ChatColor.translateAlternateColorCodes('&', "&l&7[&r&a$NAME&r&ll&7]"),
            ChatColor.translateAlternateColorCodes('&', message)
        )

        @JvmStatic
        @Mod.Instance
        lateinit var instance: OofMod
            private set
    }
}
