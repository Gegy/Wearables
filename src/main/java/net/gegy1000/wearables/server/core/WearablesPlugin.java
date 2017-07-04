package net.gegy1000.wearables.server.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.Name("wearables")
@IFMLLoadingPlugin.MCVersion("1.11.2")
@IFMLLoadingPlugin.SortingIndex(1002)
@IFMLLoadingPlugin.TransformerExclusions({ "net.ilexiconn.llibrary.server.asm" })
public class WearablesPlugin implements IFMLLoadingPlugin {
    public static boolean loaded;
    public static boolean development;

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "net.gegy1000.wearables.server.patcher.WearablesRuntimePatcher" };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        WearablesPlugin.loaded = true;
        WearablesPlugin.development = !(Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
