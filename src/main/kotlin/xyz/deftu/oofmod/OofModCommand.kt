package xyz.deftu.oofmod

import xyz.deftu.oofmod.config.OofModConfig
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.commands.annotations.Command
import xyz.unifycraft.unicore.api.commands.annotations.Default

@Command(
    name = OofMod.MODID
) class OofModCommand {
    @Default
    fun handle() {
        OofModConfig.gui()?.let {
            UniCore.getGuiHelper().showScreen(it)
        }
    }
}