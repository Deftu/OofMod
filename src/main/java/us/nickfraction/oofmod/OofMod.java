package us.nickfraction.oofmod;

import gg.essential.api.EssentialAPI;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import us.nickfraction.oofmod.command.OofModCommand;
import us.nickfraction.oofmod.listeners.OofModListener;

@Mod(
        name = OofMod.NAME,
        version = OofMod.VERSION,
        modid = OofMod.MODID
)
public class OofMod {
    public static final String MODID = "@ID@";
    public static final String VERSION = "@VERSION@";
    public static final String NAME = "@NAME@";

    public static final String DEFAULT_SOUND_URL = "https://oofmodsound.powns.dev/oof.wav";

    @Mod.Instance private static OofMod INSTANCE;
    private OofModConfig config;

    @Mod.EventHandler public void initialize(FMLInitializationEvent event) {
        config = new OofModConfig();
        config.initialize();
        MinecraftForge.EVENT_BUS.register(new OofModListener());
    }

    @Mod.EventHandler public void postInitialize(FMLPostInitializationEvent event) {
        EssentialAPI.getCommandRegistry().registerCommand(new OofModCommand());
    }

    public OofModConfig getConfig() {
        return config;
    }

    public static OofMod getInstance() {
        return INSTANCE;
    }
}
