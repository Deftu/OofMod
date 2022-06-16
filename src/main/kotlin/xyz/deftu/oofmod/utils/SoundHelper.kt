package xyz.deftu.oofmod.utils

import gg.essential.api.utils.Multithreading
import javazoom.jlgui.basicplayer.BasicController
import javazoom.jlgui.basicplayer.BasicPlayer
import javazoom.jlgui.basicplayer.BasicPlayerEvent
import javazoom.jlgui.basicplayer.BasicPlayerListener
import xyz.deftu.oofmod.OofMod
import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine.Info
import javax.sound.sampled.SourceDataLine

object SoundHelper {
    private val player = BasicPlayer().apply {
        addBasicPlayerListener(PlayerListener())
    }

    @JvmStatic
    fun playSound(file: File) {
        if (!file.exists()) OofMod.sendMessage("Sound file not found: ${file.absolutePath}").also { return }
        Multithreading.runAsync {
            player.open(file)
            player.play()
        }
    }
}

private class PlayerListener : BasicPlayerListener {
    override fun opened(stream: Any, properties: MutableMap<Any?, Any?>) {
        // Not needed.
    }

    override fun progress(
        bytesread: Int,
        microseconds: Long,
        pcmdata: ByteArray,
        properties: MutableMap<Any?, Any?>
    ) {
        // Not needed.
    }

    override fun stateUpdated(event: BasicPlayerEvent) {
        if (event.code != BasicPlayerEvent.STOPPED) return
    }

    override fun setController(controller: BasicController) {
    }
}
