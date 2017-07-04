package net.gegy1000.wearables;

import net.gegy1000.wearables.server.ServerProxy;
import net.gegy1000.wearables.server.core.WearablesPlugin;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Wearables.MODID, name = "Wearables", version = Wearables.VERSION, dependencies = "required-after:llibrary@[" + Wearables.LLIBRARY_VERSION + ",)")
public class Wearables {
    public static final String MODID = "wearables";
    public static final String VERSION = "1.1.0";
    public static final String LLIBRARY_VERSION = "1.7.6";

    @Mod.Instance(Wearables.MODID)
    public static Wearables INSTANCE;

    @SidedProxy(serverSide = "net.gegy1000.wearables.server.ServerProxy", clientSide = "net.gegy1000.wearables.client.ClientProxy")
    public static ServerProxy PROXY;

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static SimpleNetworkWrapper NETWORK_WRAPPER = new SimpleNetworkWrapper("wearables");

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        if (!WearablesPlugin.loaded) {
            System.err.println("Failed to load Wearables plugin! Missing coremod parameters! (-Dfml.coreMods.load=" + WearablesPlugin.class.getName() + ")");
            FMLCommonHandler.instance().exitJava(1, false);
        }

        Wearables.PROXY.onPreInit();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        Wearables.PROXY.onInit();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        Wearables.PROXY.onPostInit();
    }
}
