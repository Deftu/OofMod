package xyz.deftu.oofmod

import gg.essential.api.EssentialAPI
import gg.essential.api.utils.Multithreading
import gg.essential.universal.ChatColor
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import xyz.deftu.oofmod.config.OofModConfig
import xyz.deftu.oofmod.listeners.BedBreakListener
import xyz.deftu.oofmod.listeners.DeathListener
import xyz.deftu.oofmod.listeners.KillListener
import xyz.deftu.oofmod.utils.PlayerHelper
import java.util.concurrent.TimeUnit


@Mod(
    name = OofMod.NAME,
    version = OofMod.VERSION,
    modid = OofMod.MODID
) class OofMod {
    companion object {
        const val MODID = "@MOD_ID@"
        const val VERSION = "@MOD_VERSION@"
        const val NAME = "@MOD_NAME@"
        const val DEFAULT_SOUND_URL = "https://oofmodsound.powns.dev/oof.wav"
        const val DISCORD_URL = "https://shr.deftu.xyz/discord"

        @JvmStatic
        fun sendMessage(message: String) =
            EssentialAPI.getMinecraftUtil()
                .sendMessage(
                    ChatColor.translateAlternateColorCodes('&', "&l&7[&r&a$NAME&r&l&7]"),
                    ChatColor.translateAlternateColorCodes('&', message)
                )

        @Mod.Instance @JvmStatic lateinit var instance: OofMod
            private set
    }

    lateinit var config: OofModConfig
        private set

    @Mod.EventHandler
    fun initialize(event: FMLInitializationEvent) {
        PlayerHelper.initialize()
        config = OofModConfig().also { it.initialize() }

        MinecraftForge.EVENT_BUS.register(BedBreakListener())
        MinecraftForge.EVENT_BUS.register(DeathListener())
        MinecraftForge.EVENT_BUS.register(KillListener())
    }

    @Mod.EventHandler
    fun postInitialize(event: FMLPostInitializationEvent) {
        EssentialAPI.getCommandRegistry().registerCommand(OofModCommand())
    }
}
