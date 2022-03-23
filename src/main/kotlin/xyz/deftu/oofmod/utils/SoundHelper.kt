package xyz.deftu.oofmod.utils

import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine.Info
import javax.sound.sampled.SourceDataLine

object SoundHelper {
    @JvmStatic fun playSound(file: File, volume: Int) {
        if (file.exists()) {
            AudioSystem.getAudioInputStream(file).use { input ->
                val output = fetchOutputFormat(input.format)
                val info = Info(SourceDataLine::class.java, output)
                (AudioSystem.getLine(info) as SourceDataLine).use { line ->
                    line.open(output)
                    line.start()
                    play(input, line)
                    line.drain()
                    line.stop()
                }
            }

            // OofMod.sendMessage("&cAn error occurred while trying to play a sound! Please report this in ${OofMod.DISCORD_URL}.")
        }
    }

    private fun fetchOutputFormat(format: AudioFormat): AudioFormat {
        val channels = format.channels
        val rate = format.sampleRate
        return AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, channels, channels * 2, rate, false)
    }

    private fun play(input: AudioInputStream, line: SourceDataLine) {
        val buffer = ByteArray(4096)
        var n = 0
        while (n != -1) {
            line.write(buffer, 0, n)
            n = input.read(buffer, 0, buffer.size)
        }
    }
}