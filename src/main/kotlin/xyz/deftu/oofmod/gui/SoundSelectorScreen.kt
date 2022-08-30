package xyz.deftu.oofmod.gui

import gg.essential.api.EssentialAPI
import gg.essential.api.gui.EssentialGUI
import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.components.UIText
import gg.essential.elementa.components.UIWrappedText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.constraints.RelativeConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.universal.ChatColor
import gg.essential.universal.UDesktop
import net.minecraft.client.Minecraft
import xyz.deftu.oofmod.OofMod
import xyz.deftu.oofmod.utils.SoundHelper
import java.awt.Color
import java.io.File
import java.io.FileOutputStream
import java.io.FilenameFilter
import java.net.URI
import java.net.URL
import java.nio.channels.Channels

class SoundSelectorScreen(
    private val selectedSound: String,
    private val selectedSoundCallback: (String) -> Unit
) : EssentialGUI(
    ElementaVersion.V1,
    "${ChatColor.GREEN}${OofMod.NAME}"
) {
    private val soundsDir = Minecraft.getMinecraft().mcDataDir.resolve("config/Deftu/${OofMod.NAME}/Sounds")
    private val soundComponents = mutableListOf<SoundComponent>()
    private var selectedFile: File? = null
    private val soundContainer = UIContainer().constrain {
        width = RelativeConstraint()
        height = RelativeConstraint()
    }

    init {
        reload()
    }

    private fun reload() {
        content.clearChildren()
        soundContainer childOf content
        val files = soundsDir.listFiles(soundFileFilter)
        files?.let {
            if (it.isNotEmpty()) {
                for (file in it) {
                    soundComponents.add(SoundComponent(file, selectedSoundCallback, {
                        if (soundComponents.contains(it)) {
                            soundContainer.clearChildren()
                            val indexRaw = soundComponents.indexOf(it)
                            println("Prev: $indexRaw")
                            val index = if (indexRaw == 0) soundComponents.size - 1 else indexRaw - 1
                            soundComponents[index] childOf soundContainer
                        }
                    }, {
                        if (soundComponents.contains(it)) {
                            soundContainer.clearChildren()
                            val indexRaw = soundComponents.indexOf(it)
                            val index = if (indexRaw == soundComponents.size - 1) 0 else indexRaw + 1
                            soundComponents[index] childOf soundContainer
                        }
                    }).constrain {
                        x = CenterConstraint()
                        y = CenterConstraint()
                    })
                }

                if (selectedSound.isNotEmpty()) {
                    val filtered = soundComponents.filter { it.file.name == selectedSound }
                    if (filtered.isEmpty()) soundComponents[0] childOf soundContainer
                    else filtered[0] childOf soundContainer
                } else soundComponents[0] childOf soundContainer
            } else empty()
        } ?: empty()
    }

    private fun empty() {
        val text = UIWrappedText("The sounds folder is empty. Click me to download the default sound!").constrain {
            x = CenterConstraint()
            y = CenterConstraint()
        }.onMouseEnter {
            animate {
                setColorAnimation(Animations.OUT_EXP, 1f, Color.GREEN.toConstraint())
            }
        }.onMouseLeave {
            animate {
                setColorAnimation(Animations.OUT_EXP, 1f, Color.WHITE.toConstraint())
            }
        }.onMouseClick {
            try {
                val byteChannel = Channels.newChannel(URL(OofMod.DEFAULT_SOUND_URL).openStream())
                val outputStream = FileOutputStream(
                    File(
                        soundsDir,
                        OofMod.DEFAULT_SOUND_URL.substring(OofMod.DEFAULT_SOUND_URL.lastIndexOf("/"))
                    ).also { if (!it.parentFile.exists() && !it.parentFile.mkdirs()) throw IllegalStateException("Failed to make OofMod config directory.") }
                )
                outputStream.channel.transferFrom(byteChannel, 0, Long.MAX_VALUE)
            } catch (e: Exception) {
                EssentialAPI.getNotifications().push(OofMod.NAME, "Couldn't download default sound. Click me to open the download link!", action = {
                    UDesktop.browse(URI.create(OofMod.DEFAULT_SOUND_URL))
                })
            }

            reload()
        } childOf content
    }

    override fun onScreenClose() {
        selectedFile?.let {
            val config = OofMod.instance.config
            selectedSoundCallback.invoke(it.name)
            config.markDirty()
            config.writeData()
        }

        super.onScreenClose()
    }

    companion object {
        val soundFileFilter = FilenameFilter { _, name: String ->
            name.endsWith(".wav") ||
            name.endsWith(".mp3") ||
            name.endsWith(".m4a")
        }
    }

    internal class SoundComponent(
        val file: File,
        val selectedSoundCallback: (String) -> Unit,
        val prevCallback: (SoundComponent) -> Unit,
        val nextCallback: (SoundComponent) -> Unit
    ) : UIContainer() {
        init {
            constrain {
                width = ChildBasedSizeConstraint()
                height = ChildBasedSizeConstraint()
            }
        }

        val controlsContainer = UIContainer().constrain {
            x = CenterConstraint()
            y = CenterConstraint()
            width = ChildBasedSizeConstraint()
            height = ChildBasedSizeConstraint()
        } childOf this

        val prevBlock = OofButton("<", Color.RED).constrain {
            width = 32.pixels()
            height = 32.pixels()
        }.onMouseClick {
            prevCallback.invoke(this@SoundComponent)
        } childOf controlsContainer
        val interactivesContainer = UIContainer().constrain {
            x = SiblingConstraint(2f)
            width = 96.pixels()
            height = ChildBasedSizeConstraint()
        } childOf controlsContainer
        val selectBlock = OofButton("Select", Color.WHITE).constrain {
            width = 96.pixels()
            height = 32.pixels()
        }.onMouseClick {
            val config = OofMod.instance.config
            selectedSoundCallback.invoke(file.name)
            config.markDirty()
            config.writeData()
        } childOf interactivesContainer
        val previewBlock = OofButton("Preview", Color.GRAY).constrain {
            y = SiblingConstraint(2f)
            width = 96.pixels()
            height = 32.pixels()
        }.onMouseClick {
            SoundHelper.playSound(file, OofMod.instance.config.volume)
        } childOf interactivesContainer
        val nextBlock = OofButton(">", Color.RED).constrain {
            x = SiblingConstraint(2f)
            width = 32.pixels()
            height = 32.pixels()
        }.onMouseClick {
            nextCallback.invoke(this@SoundComponent)
        } childOf controlsContainer

        val title = UIText(file.name).constrain {
            x = CenterConstraint()
            y = SiblingConstraint(2f, true)
        } childOf this
    }
}