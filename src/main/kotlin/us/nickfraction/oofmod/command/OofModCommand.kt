package us.nickfraction.oofmod.command

import gg.essential.api.EssentialAPI
import gg.essential.api.commands.Command
import gg.essential.api.commands.DefaultHandler
import us.nickfraction.oofmod.OofMod

class OofModCommand : Command(OofMod.MODID) {
    @DefaultHandler fun execute() {
        EssentialAPI.getGuiUtil().openScreen(OofMod.getInstance().config.gui())
    }
}