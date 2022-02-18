package us.nickfraction.oofmod

import gg.essential.api.EssentialAPI
import gg.essential.universal.ChatColor
import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import net.minecraft.client.Minecraft
import us.nickfraction.oofmod.gui.SoundSelectorScreen
import java.io.File

class OofModConfig : Vigilant(
    Minecraft.getMinecraft().mcDataDir.resolve("config/Deftu/${OofMod.NAME}/config.toml").also { if (!it.parentFile.exists() && !it.parentFile.mkdirs()) throw IllegalStateException("Failed to make OofMod config directory.") },
    "${ChatColor.GREEN}${OofMod.NAME}"
) {
    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle",
        category = "General",
        description = "The master toggle for the mod."
    ) var toggle = true
    @Property(
        type = PropertyType.SLIDER,
        name = "Volume",
        category = "General",
        description = "The amount of decibels to increase/decrease the sound by when playing.",
        min = -50,
        max = 5
    ) var volume = 5
    @Property(
        type = PropertyType.BUTTON,
        name = "Open Sounds Menu",
        category = "General",
        description = "Opens the sound configuration menu."
    ) fun openSoundsMenu() {
        EssentialAPI.getGuiUtil().openScreen(SoundSelectorScreen())
    }

    @Property(
        type = PropertyType.TEXT,
        name = "Selected sound",
        category = "Sounds",
        hidden = true
    ) var selectedSound = ""
    val selectedSoundFile: File
        get() = Minecraft.getMinecraft().mcDataDir.resolve("config/Deftu/${OofMod.NAME}/Sounds/$selectedSound")

    init {
        hidePropertyIf("volume") { !toggle }
    }
}