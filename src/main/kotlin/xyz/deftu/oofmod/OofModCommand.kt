package xyz.deftu.oofmod

import gg.essential.api.EssentialAPI
import gg.essential.api.commands.Command
import gg.essential.api.commands.DefaultHandler
import xyz.deftu.oofmod.config.OofModConfig

object OofModCommand : Command(OofMod.ID) {
    @DefaultHandler
    fun handle() {
        val gui = OofModConfig.gui() ?: return
        EssentialAPI.getGuiUtil().openScreen(gui)
    }
}