package xyz.deftu.oofmod.config

import gg.essential.universal.ChatColor
import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import xyz.deftu.oofmod.OofMod
import java.io.File

object OofModConfig : Vigilant(
    file = File(OofMod.configDir, "config.toml"),
    guiTitle = "${ChatColor.GREEN}${OofMod.NAME}"
) {
    /* General settings. */
    @Property(
        type = PropertyType.SWITCH,
        name = "Master Toggle",
        category = "General"
    ) var toggle = true

    /* Advanced settings. */
    @Property(
        type = PropertyType.SWITCH,
        name = "Advanced Settings",
        category = "Advanced"
    ) var advancedSettings = false
    @Property(
        type = PropertyType.SWITCH,
        name = "Allow Traditional Kill",
        description = "Whether to play sounds when you kill players and they play the death animations.",
        category = "Advanced"
    ) internal var allowsTraditionalKill = true
    @Property(
        type = PropertyType.SWITCH,
        name = "Allow Traditional Death",
        description = "Whether to play sounds when you die with the normal death screen or not.",
        category = "Advanced"
    ) internal var allowsTraditionalDeath = true
    internal var regexMode = 0

    init {
        category("Advanced") {
            // RegexMode selector.
            selector(
                field = ::regexMode,
                name = "Mode",
                description = "The mode of which messages or kills are processed.",
                options = RegexMode.values().map(RegexMode::mode),
                action = {
                    println("Regex mode set to ${RegexMode.values()[it].mode}")
                }
            )
        }
    }
}
