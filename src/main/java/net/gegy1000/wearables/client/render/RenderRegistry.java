package net.gegy1000.wearables.client.render;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.render.block.DisplayMannequinRenderer;
import net.gegy1000.wearables.client.render.block.MannequinHeadStandRenderer;
import net.gegy1000.wearables.client.render.block.WearableAssemblerRenderer;
import net.gegy1000.wearables.client.render.block.WearableColouriserRenderer;
import net.gegy1000.wearables.client.render.block.WearableFabricatorRenderer;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.gegy1000.wearables.client.render.component.chest.BowTieRenderer;
import net.gegy1000.wearables.client.render.component.chest.JetpackRenderer;
import net.gegy1000.wearables.client.render.component.chest.ModOffCapeRenderer;
import net.gegy1000.wearables.client.render.component.chest.Shirt1Renderer;
import net.gegy1000.wearables.client.render.component.chest.Shirt2Renderer;
import net.gegy1000.wearables.client.render.component.chest.TShirt1Renderer;
import net.gegy1000.wearables.client.render.component.chest.TShirt2Renderer;
import net.gegy1000.wearables.client.render.component.chest.TieRenderer;
import net.gegy1000.wearables.client.render.component.chest.WingsRenderer;
import net.gegy1000.wearables.client.render.component.feet.FlippersRenderer;
import net.gegy1000.wearables.client.render.component.feet.Shoes1Renderer;
import net.gegy1000.wearables.client.render.component.head.DragonHornsRenderer;
import net.gegy1000.wearables.client.render.component.head.Glasses1Renderer;
import net.gegy1000.wearables.client.render.component.head.Helmet1Renderer;
import net.gegy1000.wearables.client.render.component.head.Helmet2Renderer;
import net.gegy1000.wearables.client.render.component.head.NightVisionGogglesRenderer;
import net.gegy1000.wearables.client.render.component.head.Retro3DGlassesRenderer;
import net.gegy1000.wearables.client.render.component.head.RoundGlassesRenderer;
import net.gegy1000.wearables.client.render.component.head.TopHatRenderer;
import net.gegy1000.wearables.client.render.component.legs.Pants1Renderer;
import net.gegy1000.wearables.client.render.item.WearableComponentRenderer;
import net.gegy1000.wearables.client.render.item.WearableItemRenderer;
import net.gegy1000.wearables.server.api.item.RegisterItemModel;
import net.gegy1000.wearables.server.block.BlockRegistry;
import net.gegy1000.wearables.server.block.DisplayMannequinBlock;
import net.gegy1000.wearables.server.block.MannequinHeadStandBlock;
import net.gegy1000.wearables.server.block.WearableAssemblerBlock;
import net.gegy1000.wearables.server.block.WearableColouriserBlock;
import net.gegy1000.wearables.server.block.WearableFabricatorBlock;
import net.gegy1000.wearables.server.block.entity.DisplayMannequinEntity;
import net.gegy1000.wearables.server.block.entity.MannequinHeadStandEntity;
import net.gegy1000.wearables.server.block.entity.machine.WearableAssemblerEntity;
import net.gegy1000.wearables.server.block.entity.machine.WearableColouriserEntity;
import net.gegy1000.wearables.server.block.entity.machine.WearableFabricatorEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableChestItemEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableComponentEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableFeetItemEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableHeadItemEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableLegsItemEntity;
import net.gegy1000.wearables.server.item.ItemRegistry;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.HashMap;
import java.util.Map;

public class RenderRegistry {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final Map<String, ComponentRenderer> COMPONENT_RENDERERS = new HashMap<>();

    public static void onPreInit() {
        for (Block block : BlockRegistry.BLOCKS) {
            String name = block.getUnlocalizedName().substring("tile.".length());
            if (block instanceof RegisterItemModel) {
                RenderRegistry.register(block, ((RegisterItemModel) block).getResource(name), "inventory");
            }
        }

        for (Item item : ItemRegistry.ITEMS) {
            String name = item.getUnlocalizedName().substring("item.".length());
            if (item instanceof RegisterItemModel) {
                RenderRegistry.register(item, ((RegisterItemModel) item).getResource(name), "inventory");
            }
        }

        ClientRegistry.bindTileEntitySpecialRenderer(DisplayMannequinEntity.class, new DisplayMannequinRenderer());

        ClientRegistry.bindTileEntitySpecialRenderer(WearableComponentEntity.class, new WearableComponentRenderer());
        ForgeHooksClient.registerTESRItemStack(ItemRegistry.WEARABLE_COMPONENT, 0, WearableComponentEntity.class);

        ClientRegistry.bindTileEntitySpecialRenderer(WearableHeadItemEntity.class, new WearableItemRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WearableChestItemEntity.class, new WearableItemRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WearableLegsItemEntity.class, new WearableItemRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WearableFeetItemEntity.class, new WearableItemRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(MannequinHeadStandEntity.class, new MannequinHeadStandRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WearableFabricatorEntity.class, new WearableFabricatorRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WearableColouriserEntity.class, new WearableColouriserRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(WearableAssemblerEntity.class, new WearableAssemblerRenderer());

        ForgeHooksClient.registerTESRItemStack(ItemRegistry.WEARABLE_HEAD, 0, WearableHeadItemEntity.class);
        ForgeHooksClient.registerTESRItemStack(ItemRegistry.WEARABLE_CHEST, 0, WearableChestItemEntity.class);
        ForgeHooksClient.registerTESRItemStack(ItemRegistry.WEARABLE_LEGS, 0, WearableLegsItemEntity.class);
        ForgeHooksClient.registerTESRItemStack(ItemRegistry.WEARABLE_FEET, 0, WearableFeetItemEntity.class);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlockRegistry.WEARABLE_FABRICATOR), 0, WearableFabricatorEntity.class);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlockRegistry.WEARABLE_COLOURISER), 0, WearableColouriserEntity.class);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(BlockRegistry.WEARABLE_ASSEMBLER), 0, WearableAssemblerEntity.class);

        RenderRegistry.register(ComponentRegistry.GLASSES_1, new Glasses1Renderer());
        RenderRegistry.register(ComponentRegistry.TOP_HAT, new TopHatRenderer());
        RenderRegistry.register(ComponentRegistry.RETRO_3D_GLASSES, new Retro3DGlassesRenderer());
        RenderRegistry.register(ComponentRegistry.HARRY_POTTER_GLASSES, new RoundGlassesRenderer());
        RenderRegistry.register(ComponentRegistry.MODOFF_CAPE, new ModOffCapeRenderer());
        RenderRegistry.register(ComponentRegistry.BOW_TIE, new BowTieRenderer());
        RenderRegistry.register(ComponentRegistry.TIE, new TieRenderer());
        RenderRegistry.register(ComponentRegistry.FLIPPERS, new FlippersRenderer());
        RenderRegistry.register(ComponentRegistry.JETPACK, new JetpackRenderer());
        RenderRegistry.register(ComponentRegistry.SHOES_1, new Shoes1Renderer());
        RenderRegistry.register(ComponentRegistry.PANTS_1, new Pants1Renderer());
        RenderRegistry.register(ComponentRegistry.HELMET_1, new Helmet1Renderer());
        RenderRegistry.register(ComponentRegistry.HELMET_2, new Helmet2Renderer());
        RenderRegistry.register(ComponentRegistry.SHIRT_1, new Shirt1Renderer());
        RenderRegistry.register(ComponentRegistry.SHIRT_2, new Shirt2Renderer());
        RenderRegistry.register(ComponentRegistry.DRAGON_HORNS, new DragonHornsRenderer());
        RenderRegistry.register(ComponentRegistry.T_SHIRT_1, new TShirt1Renderer());
        RenderRegistry.register(ComponentRegistry.T_SHIRT_2, new TShirt2Renderer());
        RenderRegistry.register(ComponentRegistry.WINGS, new WingsRenderer());
        RenderRegistry.register(ComponentRegistry.NIGHT_VISION_GOGGLES, new NightVisionGogglesRenderer());

        ModelLoader.setCustomStateMapper(BlockRegistry.DISPLAY_MANNEQUIN, new StateMap.Builder().ignore(DisplayMannequinBlock.FACING, DisplayMannequinBlock.HALF).build());
        ModelLoader.setCustomStateMapper(BlockRegistry.HEAD_STAND_MANNEQUIN, new StateMap.Builder().ignore(MannequinHeadStandBlock.FACING).build());
        ModelLoader.setCustomStateMapper(BlockRegistry.WEARABLE_FABRICATOR, new StateMap.Builder().ignore(WearableFabricatorBlock.FACING).build());
        ModelLoader.setCustomStateMapper(BlockRegistry.WEARABLE_ASSEMBLER, new StateMap.Builder().ignore(WearableAssemblerBlock.FACING, WearableAssemblerBlock.HALF).build());
        ModelLoader.setCustomStateMapper(BlockRegistry.WEARABLE_COLOURISER, new StateMap.Builder().ignore(WearableColouriserBlock.FACING).build());
    }

    public static void register(Item item, String path, String type) {
        RenderRegistry.register(item, 0, path, type);
    }

    public static void register(Item item, int meta, String path, String type) {
        ModelResourceLocation resource = new ModelResourceLocation(Wearables.MODID + ":" + path, type);
        ModelLoader.setCustomModelResourceLocation(item, meta, resource);
    }

    public static void register(Block block, int meta, String path, String type) {
        RenderRegistry.register(Item.getItemFromBlock(block), meta, path, type);
    }

    public static void register(Block block, final String path, final String type) {
        RenderRegistry.register(block, 0, path, type);
    }

    public static void register(WearableComponentType type, ComponentRenderer renderer) {
        COMPONENT_RENDERERS.put(type.getIdentifier(), renderer);
    }

    public static ComponentRenderer getRenderer(String identifier) {
        return COMPONENT_RENDERERS.get(identifier);
    }
}
