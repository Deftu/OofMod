package xyz.deftu.oofmod

import gg.essential.api.EssentialAPI
import gg.essential.universal.ChatColor
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import xyz.deftu.oofmod.config.OofModConfig
import xyz.deftu.oofmod.handlers.impl.RegexHandler
import xyz.deftu.oofmod.listeners.BedBreakListener
import xyz.deftu.oofmod.listeners.DeathListener
import xyz.deftu.oofmod.listeners.KillListener
import xyz.deftu.oofmod.utils.PlayerHelper
import xyz.unifycraft.unicore.api.UniCore
import java.io.File

@Mod(
    name = OofMod.NAME,
    version = OofMod.VERSION,
    modid = OofMod.MODID
) class OofMod {
    @Mod.EventHandler
    fun initialize(event: FMLInitializationEvent) {
        configDir = File(File(UniCore.getFileHelper().configDir, "Deftu"), NAME)
        soundsDir = File(configDir, "Sounds")
        if (!configDir.exists() && !configDir.mkdirs())
            throw IllegalStateException("Failed to make $NAME config directory.")
        if (!soundsDir.exists() && !soundsDir.mkdirs())
            throw IllegalStateException("Failed to make $NAME sounds directory.")

        // Start the player utility checker.
        PlayerHelper.initialize()
        // Initialize the mod config via Vigilance's in-built initializer method.
        OofModConfig.initialize()
        // Start the regex handler thread.
        RegexHandler.start(configDir.resolve("regex.json").toPath())

        // Register our command with UniCOre's command registry.
        UniCore.getCommandRegistry().registerCommand(OofModCommand())

        // Register the event listeners so sounds can be played when needed.
        MinecraftForge.EVENT_BUS.register(BedBreakListener())
        MinecraftForge.EVENT_BUS.register(DeathListener())
        MinecraftForge.EVENT_BUS.register(KillListener())
    }

    companion object {
        const val MODID = "@ID@"
        const val VERSION = "@VERSION@"
        const val NAME = "@NAME@"
        const val DEFAULT_SOUND_URL = "https://oofmodsound.powns.dev/oof.wav"
        const val DISCORD_URL = "https://discord.gg/dFb277Kexf"

        lateinit var configDir: File
            private set
        lateinit var soundsDir: File
            private set

        @JvmStatic
        fun sendMessage(message: String) =
            EssentialAPI.getMinecraftUtil()
                .sendMessage(
                    ChatColor.translateAlternateColorCodes('&', "&l&7[&r&a$NAME&r&ll&7]"),
                    ChatColor.translateAlternateColorCodes('&', message)
                )

        @Mod.Instance @JvmStatic lateinit var instance: OofMod
            private set
    }
}
