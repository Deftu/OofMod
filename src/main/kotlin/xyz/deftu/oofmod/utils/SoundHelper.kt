package xyz.deftu.oofmod.utils

import xyz.deftu.oofmod.OofMod
import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.FloatControl

object SoundHelper {
    @JvmStatic fun playSound(file: File, volume: Int) {
        if (file.exists()) {
            try {
                val audio = AudioSystem.getAudioInputStream(file)
                val clip = AudioSystem.getClip()
                clip.open(audio)
                val control = clip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
                control.value = volume.toFloat()
                clip.start()
            } catch (e: Exception) {
                OofMod.sendMessage("&cAn error occurred while trying to play a sound! Please report this in ${OofMod.DISCORD_URL}. (${e::class.java.simpleName})")
                e.printStackTrace()
            }
        }
    }
}
