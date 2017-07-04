package net.gegy1000.wearables.server.config;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.network.SyncConfigMessage;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Wearables.MODID, category = "")
@Mod.EventBusSubscriber
public class WearablesConfig {
    @Config.Name("restrictions")
    @Config.LangKey("config.wearables.restrictions")
    public static Restrictions restrictions = new Restrictions();

    private static Restrictions globalRestrictions = new Restrictions();

    public static void updateGlobal(Restrictions restrictions) {
        globalRestrictions = restrictions;
    }

    public static Restrictions getGlobalRestrictions(World world) {
        return world.isRemote ? globalRestrictions : restrictions;
    }

    public static class Restrictions {
        @Config.Name("allow_wing_flight")
        @Config.LangKey("config.wearables.allow_wing_flight")
        @Config.Comment("If false, players cannot fly with the wing component")
        public boolean allowWingFlight = true;

        @Config.Name("allow_jetpack_flight")
        @Config.LangKey("config.wearables.allow_jetpack_flight")
        @Config.Comment("If false, players cannot fly with the jetpack component")
        public boolean allowJetpackFlight = true;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Wearables.MODID)) {
            ConfigManager.sync(Wearables.MODID, Config.Type.INSTANCE);
            if (event.isWorldRunning()) {
                Wearables.NETWORK_WRAPPER.sendToAll(new SyncConfigMessage());
            }
        }
    }
}
