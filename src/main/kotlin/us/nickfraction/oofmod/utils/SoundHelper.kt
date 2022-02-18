package us.nickfraction.oofmod.utils

import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.FloatControl

object SoundHelper {
    @JvmStatic fun playSound(file: File, volume: Int) {
        val audio = AudioSystem.getAudioInputStream(file)
        val clip = AudioSystem.getClip()
        clip.open(audio)
        val control = clip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
        control.value = volume.toFloat()
        clip.start()
    }
}