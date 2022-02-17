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
    File(File(Minecraft.getMinecraft().mcDataDir, "config"), "${OofMod.MODID}.toml"),
    "${ChatColor.GREEN}${OofMod.NAME}"
) {
    @Property(
        type = PropertyType.SWITCH,
        name = "Toggle",
        category = "General"
    ) var toggle = true
    @Property(
        type = PropertyType.SLIDER,
        name = "Volume",
        category = "General",
        min = -10,
        max = 30
    ) var volume = -10f
    @Property(
        type = PropertyType.BUTTON,
        name = "Open Sounds Menu",
        category = "General"
    ) fun openSoundsMenu() {
        EssentialAPI.getGuiUtil().openScreen(SoundSelectorScreen())
    }

    init {
        hidePropertyIf("volume") { !toggle }
    }
}