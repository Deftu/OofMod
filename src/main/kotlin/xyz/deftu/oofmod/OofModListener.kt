package xyz.deftu.oofmod

import gg.essential.api.utils.Multithreading
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import xyz.deftu.oofmod.config.OofModConfig
import xyz.deftu.oofmod.config.OofMode
import xyz.deftu.oofmod.utils.SoundHelper
import java.io.File

object OofModListener {

    @SubscribeEvent
    fun onChatReceived(event: ClientChatReceivedEvent) {
        if (!OofModConfig.toggle) return
        if (event.type.toInt() != 0) return
        println("Hi, Computer!")
        Multithreading.runAsync {
            val text = event.message.unformattedText
            if (OofMode.regexMode.regexes.any {
                    println("Hello, World!")
                it.containsMatchIn(text)
            }) {
                println("Hello, World! 2")
                SoundHelper.playSound(File(OofMod.soundsDir, "oof.wav"))
                return@runAsync
            }
        }
    }

}
