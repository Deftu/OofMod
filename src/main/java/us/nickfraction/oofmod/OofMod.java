package us.nickfraction.oofmod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
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

    @Mod.Instance private static OofMod INSTANCE;
    private OofModConfig config;

    @EventHandler public void initialize(FMLInitializationEvent event) {
        config = new OofModConfig();
        config.initialize();
        MinecraftForge.EVENT_BUS.register(new OofModListener(this));
    }

    public OofModConfig getConfig() {
        return config;
    }

    public static OofMod getInstance() {
        return INSTANCE;
    }
}
