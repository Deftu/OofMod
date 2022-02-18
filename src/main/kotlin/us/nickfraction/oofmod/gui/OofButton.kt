package us.nickfraction.oofmod.gui

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.RelativeConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.util.ResourceLocation
import java.awt.Color

class OofButton(
    text: String,
    outlineColor: Color?
) : UIContainer() {
    val block = UIBlock(Color(31, 31, 31)).constrain {
        width = RelativeConstraint()
        height = RelativeConstraint()
    } effect OutlineEffect(Color(0, 0, 0, 0), 1f) childOf this
    val textComponent = UIText(text).constrain {
        x = CenterConstraint()
        y = CenterConstraint()
    } childOf block

    init {
        outlineColor?.let {
            val outlineEffect = block.effects[0] as OutlineEffect
            onMouseEnter {
                outlineEffect::color.animate(Animations.OUT_EXP, 1f, it)
            }.onMouseLeave {
                outlineEffect::color.animate(Animations.OUT_EXP, 1f, Color(0, 0, 0, 0))
            }.onMouseClick {
                Minecraft.getMinecraft().soundHandler.playSound(PositionedSoundRecord.create(ResourceLocation("gui.button.press"), 1f))
            }
        }
    }
}