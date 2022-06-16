package xyz.deftu.oofmod.utils

import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent


object PlayerHelper {
    private var initialized = false
    private var tickCounter = 0
    var playerName = ""
        private set

    fun initialize() {
        if (!initialized) {
            initialized = true
            MinecraftForge.EVENT_BUS.register(PlayerHelper)
        }
    }

    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent) {
        tickCounter++
        if (tickCounter % 20 != 0) return
        val player = Minecraft.getMinecraft().thePlayer ?: return
        val sendQueue = player.sendQueue ?: return
        for (networkPlayerInfo in sendQueue.playerInfoMap) {
            val gameProfile = networkPlayerInfo.gameProfile
            if (gameProfile.id == null || gameProfile.id != Minecraft.getMinecraft().session.profile.id) continue
            playerName = gameProfile.name
            break
        }

        tickCounter = 0
    }
}