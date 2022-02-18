package us.nickfraction.oofmod.listeners;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import us.nickfraction.oofmod.OofMod;
import us.nickfraction.oofmod.utils.SoundHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OofModListener {
    private final Minecraft mc = Minecraft.getMinecraft();
    private String nameToCheck = "";

    @SubscribeEvent
    public void onDeathMessage(ClientChatReceivedEvent event) {
        if (nameToCheck.isEmpty()) nameToCheck = mc.thePlayer.getName();
        String line = event.message.getUnformattedText();
        String[] splitLine = line.split(" ");
        if (!OofMod.getInstance().getConfig().getToggle() || OofMod.getInstance().getConfig().getSelectedSound().isEmpty() || line.isEmpty() || splitLine.length == 0)
            return;

        String killMessageRegex = "(\\w{1,16}).+ (by|of|to|for|with) (" + nameToCheck + ")";
        String usernamePatternRegex = "^[a-zA-Z0-9_-]{3,16}$";

        Pattern killMessagePattern = Pattern.compile(killMessageRegex);
        Pattern usernamePattern = Pattern.compile(usernamePatternRegex);

        Matcher killMessageMatcher = killMessagePattern.matcher(line);
        Matcher usernameMatcher = usernamePattern.matcher(splitLine[0]);

        if (usernameMatcher.matches() && killMessageMatcher.find()) {
            String killed = killMessageMatcher.group(1);
            if (!killed.equals(nameToCheck)) {
                SoundHelper.playSound(
                        OofMod.getInstance().getConfig().getSelectedSoundFile(),
                        OofMod.getInstance().getConfig().getVolume()
                );
            }
        }
    }

    @SubscribeEvent
    public void profileCheck(TickEvent.ClientTickEvent event) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;

        NetHandlerPlayClient sendQueue = player.sendQueue;
        if (sendQueue == null) return;

        for (NetworkPlayerInfo networkPlayerInfo : sendQueue.getPlayerInfoMap()) {
            GameProfile gameProfile = networkPlayerInfo.getGameProfile();

            if (gameProfile.getId() != null && gameProfile.getId().equals(Minecraft.getMinecraft().getSession().getProfile().getId())) {
                nameToCheck = gameProfile.getName();
                break;
            }
        }
    }
}
