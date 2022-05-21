package xyz.deftu.oofmod.listeners

import gg.essential.universal.ChatColor
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import xyz.deftu.oofmod.OofMod
import xyz.deftu.oofmod.config.OofModConfig
import xyz.deftu.oofmod.handlers.impl.RegexHandler
import xyz.deftu.oofmod.utils.PlayerHelper
import xyz.deftu.oofmod.utils.SoundHelper
import xyz.deftu.oofmod.utils.checkEach

class BedBreakListener {
    @SubscribeEvent
    fun onChatMessageReceived(event: ClientChatReceivedEvent) {
        if (!OofModConfig.bedBreakToggle) return
        if (OofModConfig.selectedBedBreakSound.isEmpty()) return
        val line = ChatColor.stripControlCodes(event.message.unformattedText)
        line?.let {
            val split = it.split(" ")
            if (split.isEmpty()) return
            val matcher = RegexHandler.cache.getOrCache("bed_break").checkEach(it)
            println(matcher)
            if (matcher.find() && matcher.group("username") == PlayerHelper.playerName) {
                println("I'm supposed to be playing a sound right now :)")
                SoundHelper.playSound(OofModConfig.selectedBedBreakSoundFile, OofModConfig.volume)
            }
        }
    }
}