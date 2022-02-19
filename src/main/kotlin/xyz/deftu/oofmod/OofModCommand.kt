package xyz.deftu.oofmod

import gg.essential.api.EssentialAPI
import gg.essential.api.commands.Command
import gg.essential.api.commands.DefaultHandler
import xyz.deftu.oofmod.OofMod

class OofModCommand : Command(OofMod.MODID) {
    @DefaultHandler fun execute() {
        EssentialAPI.getGuiUtil().openScreen(OofMod.instance.config.gui())
    }
}