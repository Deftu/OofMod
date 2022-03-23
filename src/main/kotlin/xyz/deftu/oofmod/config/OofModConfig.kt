package xyz.deftu.oofmod.config

import gg.essential.api.EssentialAPI
import gg.essential.universal.ChatColor
import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import net.minecraft.client.Minecraft
import net.minecraft.launchwrapper.Launch
import xyz.deftu.oofmod.OofMod
import xyz.deftu.oofmod.gui.SoundSelectorScreen
import java.io.File
import java.util.regex.Pattern

object OofModConfig : Vigilant(
    File(OofMod.configDir, "config.toml"),
    "${ChatColor.GREEN}${OofMod.NAME}"
) {
    /* General settings. */
    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle",
        category = "General",
        description = "The master toggle for the mod."
    ) var toggle = true

    /* Advanced settings. */
    @Property(
        type = PropertyType.SWITCH,
        name = "Advanced Settings",
        category = "Advanced",
        description = "A toggle for the advanced settings of the mod."
    ) var advancedSettings = false
    @Property(
        type = PropertyType.SLIDER,
        name = "Volume",
        category = "Advanced",
        description = "The amount of decibels to increase/decrease the sound by when playing.",
        min = -50,
        max = 5
    ) var volume = 5

    /* Death sound settings. */
    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle",
        category = "Death Sound",
        description = "The death sound toggle for the mod."
    ) var deathToggle = true
    @Property(
        type = PropertyType.BUTTON,
        name = "Open Sounds Menu",
        category = "Death Sound",
        description = "Opens the sound configuration menu."
    ) fun openDeathSoundsMenu() {
        EssentialAPI.getGuiUtil().openScreen(SoundSelectorScreen(selectedDeathSound) {
            selectedDeathSound = it
        })
    }

    /* Kill sound settings. */
    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle",
        category = "Kill Sound",
        description = "The kill sound toggle for the mod."
    ) var killToggle = true
    @Property(
        type = PropertyType.BUTTON,
        name = "Open Sounds Menu",
        category = "Kill Sound",
        description = "Opens the sound configuration menu."
    ) fun openKillSoundsMenu() {
        EssentialAPI.getGuiUtil().openScreen(SoundSelectorScreen(selectedKillSound) {
            selectedKillSound = it
        })
    }

    /* Bed break sound settings. */
    @Property(
        type = PropertyType.PARAGRAPH,
        name = "Disclaimer",
        category = "Bed Break Sound",
        description = "!! THIS SOUND IS EXCLUSIVE TO HYPIXEL !!"
    ) var bedBreakDisclaimer = ""
    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle",
        category = "Bed Break Sound",
        description = "The bed break sound toggle for the mod."
    ) var bedBreakToggle = true
    @Property(
        type = PropertyType.BUTTON,
        name = "Open Sounds Menu",
        category = "Bed Break Sound",
        description = "Opens the sound configuration menu."
    ) fun openBedBreakSoundsMenu() {
        EssentialAPI.getGuiUtil().openScreen(SoundSelectorScreen(selectedBedBreakSound) {
            selectedBedBreakSound = it
        })
    }

    /* Store the currently selected sounds. */
    @Property(
        type = PropertyType.TEXT,
        name = "Selected Death Sound",
        category = "Sounds",
        hidden = true
    ) var selectedDeathSound = ""
    @Property(
        type = PropertyType.TEXT,
        name = "Selected Kill Sound",
        category = "Sounds",
        hidden = true
    ) var selectedKillSound = ""
    @Property(
        type = PropertyType.TEXT,
        name = "Selected Bed Break Sound",
        category = "Sounds",
        hidden = true
    ) var selectedBedBreakSound = ""
    val selectedDeathSoundFile: File
        get() = Minecraft.getMinecraft().mcDataDir.resolve("config/Deftu/${OofMod.NAME}/Sounds/$selectedDeathSound")
    val selectedKillSoundFile: File
        get() = Minecraft.getMinecraft().mcDataDir.resolve("config/Deftu/${OofMod.NAME}/Sounds/$selectedKillSound")
    val selectedBedBreakSoundFile: File
        get() = Minecraft.getMinecraft().mcDataDir.resolve("config/Deftu/${OofMod.NAME}/Sounds/$selectedBedBreakSound")

    init {
        addDependency("volume", "advancedSettings")
    }
}