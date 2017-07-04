package net.gegy1000.wearables.client.model.component;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.component.chest.BowTieModel;
import net.gegy1000.wearables.client.model.component.chest.JetpackModel;
import net.gegy1000.wearables.client.model.component.chest.ModOffCapeModel;
import net.gegy1000.wearables.client.model.component.chest.Shirt1Model;
import net.gegy1000.wearables.client.model.component.chest.Shirt1ThinModel;
import net.gegy1000.wearables.client.model.component.chest.Shirt2Model;
import net.gegy1000.wearables.client.model.component.chest.Shirt2ThinModel;
import net.gegy1000.wearables.client.model.component.chest.TShirt1Model;
import net.gegy1000.wearables.client.model.component.chest.TShirt1ThinModel;
import net.gegy1000.wearables.client.model.component.chest.TShirt2Model;
import net.gegy1000.wearables.client.model.component.chest.TShirt2ThinModel;
import net.gegy1000.wearables.client.model.component.chest.TieModel;
import net.gegy1000.wearables.client.model.component.chest.WingsModel;
import net.gegy1000.wearables.client.model.component.feet.FlippersModel;
import net.gegy1000.wearables.client.model.component.feet.Shoes1Model;
import net.gegy1000.wearables.client.model.component.head.DragonHornsModel;
import net.gegy1000.wearables.client.model.component.head.Glasses1Model;
import net.gegy1000.wearables.client.model.component.head.Helmet1Model;
import net.gegy1000.wearables.client.model.component.head.Helmet2Model;
import net.gegy1000.wearables.client.model.component.head.NightVisionGogglesModel;
import net.gegy1000.wearables.client.model.component.head.Retro3DGlassesModel;
import net.gegy1000.wearables.client.model.component.head.RoundGlassesModel;
import net.gegy1000.wearables.client.model.component.head.TopHatModel;
import net.gegy1000.wearables.client.model.component.legs.Pants1Model;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Wearables.MODID)
public class ComponentModelRegistry {
    public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(Wearables.MODID, "component_models");

    private static IForgeRegistry<WearableComponentModel> registry;

    @SubscribeEvent
    public static void onNewRegistry(RegistryEvent.NewRegistry event) {
        registry = new RegistryBuilder<WearableComponentModel>()
                .setType(WearableComponentModel.class)
                .setName(REGISTRY_NAME)
                .disableSaving()
                .create();
    }

    @SubscribeEvent
    public static void onRegister(RegistryEvent.Register<WearableComponentModel> event) {
        IForgeRegistry<WearableComponentModel> registry = event.getRegistry();

        registry.register(new BowTieModel().setRegistryName(new ResourceLocation(Wearables.MODID, "bow_tie")));
        registry.register(new TieModel().setRegistryName(new ResourceLocation(Wearables.MODID, "tie")));

        registry.register(new JetpackModel().setRegistryName(new ResourceLocation(Wearables.MODID, "jetpack")));
        registry.register(new ModOffCapeModel().setRegistryName(new ResourceLocation(Wearables.MODID, "modoff_cape")));

        registry.register(new Pants1Model().setRegistryName(new ResourceLocation(Wearables.MODID, "pants_1")));

        registry.register(new Shirt1Model().setRegistryName(new ResourceLocation(Wearables.MODID, "shirt_1")));
        registry.register(new Shirt1ThinModel().setRegistryName(new ResourceLocation(Wearables.MODID, "shirt_1_thin")));
        registry.register(new Shirt2Model().setRegistryName(new ResourceLocation(Wearables.MODID, "shirt_2")));
        registry.register(new Shirt2ThinModel().setRegistryName(new ResourceLocation(Wearables.MODID, "shirt_2_thin")));

        registry.register(new TShirt1Model().setRegistryName(new ResourceLocation(Wearables.MODID, "tshirt_1")));
        registry.register(new TShirt1ThinModel().setRegistryName(new ResourceLocation(Wearables.MODID, "tshirt_1_thin")));
        registry.register(new TShirt2Model().setRegistryName(new ResourceLocation(Wearables.MODID, "tshirt_2")));
        registry.register(new TShirt2ThinModel().setRegistryName(new ResourceLocation(Wearables.MODID, "tshirt_2_thin")));

        registry.register(new WingsModel().setRegistryName(new ResourceLocation(Wearables.MODID, "wings")));

        registry.register(new FlippersModel().setRegistryName(new ResourceLocation(Wearables.MODID, "flippers")));
        registry.register(new Shoes1Model().setRegistryName(new ResourceLocation(Wearables.MODID, "shoes_1")));

        registry.register(new DragonHornsModel().setRegistryName(new ResourceLocation(Wearables.MODID, "dragon_horns")));

        registry.register(new Glasses1Model().setRegistryName(new ResourceLocation(Wearables.MODID, "glasses_1")));
        registry.register(new NightVisionGogglesModel().setRegistryName(new ResourceLocation(Wearables.MODID, "night_vision_goggles")));
        registry.register(new Retro3DGlassesModel().setRegistryName(new ResourceLocation(Wearables.MODID, "retro_3d_glasses")));
        registry.register(new RoundGlassesModel().setRegistryName(new ResourceLocation(Wearables.MODID, "round_glasses")));

        registry.register(new Helmet1Model().setRegistryName(new ResourceLocation(Wearables.MODID, "helmet_1")));
        registry.register(new Helmet2Model().setRegistryName(new ResourceLocation(Wearables.MODID, "helmet_2")));

        registry.register(new TopHatModel().setRegistryName(new ResourceLocation(Wearables.MODID, "top_hat")));
    }

    public static IForgeRegistry<WearableComponentModel> getRegistry() {
        return registry;
    }
}
