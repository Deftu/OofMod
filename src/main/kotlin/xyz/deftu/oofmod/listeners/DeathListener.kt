package xyz.deftu.oofmod.listeners

import gg.essential.universal.ChatColor
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import xyz.deftu.oofmod.OofMod
import xyz.deftu.oofmod.config.OofModConfig
import xyz.deftu.oofmod.utils.PlayerHelper
import xyz.deftu.oofmod.utils.SoundHelper

class DeathListener {
    @SubscribeEvent
    fun onChatMessageReceived(event: ClientChatReceivedEvent) {
        if (!OofModConfig.deathToggle) return
        if (OofModConfig.selectedDeathSound.isEmpty()) return
        val line = ChatColor.stripControlCodes(event.message.unformattedText)
        line?.let {
            val split = it.split(" ")
            if (split.isEmpty()) return
            /*val killMessageMatcher = OofMod.instance.config.killDeathPattern.matcher(it)
            if (killMessageMatcher.find() && killMessageMatcher.group("username") == PlayerHelper.playerName) {
                SoundHelper.playSound(OofMod.instance.config.selectedDeathSoundFile, OofMod.instance.config.volume)
            }*/
        }
    }
}