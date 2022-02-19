package xyz.deftu.oofmod.listeners

import gg.essential.universal.ChatColor
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import xyz.deftu.oofmod.OofMod
import xyz.deftu.oofmod.utils.PlayerHelper
import xyz.deftu.oofmod.utils.SoundHelper

class BedBreakListener {
    @SubscribeEvent
    fun onChatMessageReceived(event: ClientChatReceivedEvent) {
        if (!OofMod.instance.config.bedBreakToggle) return
        if (OofMod.instance.config.selectedBedBreakSound.isEmpty()) return
        val line = ChatColor.stripControlCodes(event.message.unformattedText)
        line?.let {
            val split = it.split(" ")
            if (split.isEmpty()) return
            val bedBreakMatcher = OofMod.instance.config.bedBreakPattern.matcher(it)
            if (bedBreakMatcher.find() && bedBreakMatcher.group("username") == PlayerHelper.playerName) {
                SoundHelper.playSound(OofMod.instance.config.selectedBedBreakSoundFile, OofMod.instance.config.volume)
            }
        }
    }
}