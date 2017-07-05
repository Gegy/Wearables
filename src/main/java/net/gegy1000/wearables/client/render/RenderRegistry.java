package net.gegy1000.wearables.client.render;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.render.block.DisplayMannequinRenderer;
import net.gegy1000.wearables.client.render.block.MannequinHeadStandRenderer;
import net.gegy1000.wearables.client.render.block.WearableAssemblerRenderer;
import net.gegy1000.wearables.client.render.block.WearableColouriserRenderer;
import net.gegy1000.wearables.client.render.block.WearableFabricatorRenderer;
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
import net.gegy1000.wearables.server.block.entity.wearable.WearableFeetItemEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableHeadItemEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableLegsItemEntity;
import net.gegy1000.wearables.server.item.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Wearables.MODID)
public class RenderRegistry {
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event) {
        for (Block block : BlockRegistry.getBlocks()) {
            String name = block.getRegistryName().getResourcePath();
            if (block instanceof RegisterItemModel) {
                RenderRegistry.register(block, ((RegisterItemModel) block).getResource(name), "inventory");
            }
        }

        for (Item item : ItemRegistry.getItems()) {
            String name = item.getRegistryName().getResourcePath();
            if (item instanceof RegisterItemModel) {
                RenderRegistry.register(item, ((RegisterItemModel) item).getResource(name), "inventory");
            }
        }

        ModelLoader.setCustomStateMapper(BlockRegistry.DISPLAY_MANNEQUIN, new StateMap.Builder().ignore(DisplayMannequinBlock.FACING, DisplayMannequinBlock.HALF).build());
        ModelLoader.setCustomStateMapper(BlockRegistry.MANNEQUIN_HEAD_STAND, new StateMap.Builder().ignore(MannequinHeadStandBlock.FACING).build());
        ModelLoader.setCustomStateMapper(BlockRegistry.WEARABLE_FABRICATOR, new StateMap.Builder().ignore(WearableFabricatorBlock.FACING).build());
        ModelLoader.setCustomStateMapper(BlockRegistry.WEARABLE_ASSEMBLER, new StateMap.Builder().ignore(WearableAssemblerBlock.FACING, WearableAssemblerBlock.HALF).build());
        ModelLoader.setCustomStateMapper(BlockRegistry.WEARABLE_COLOURISER, new StateMap.Builder().ignore(WearableColouriserBlock.FACING).build());

        ClientRegistry.bindTileEntitySpecialRenderer(DisplayMannequinEntity.class, new DisplayMannequinRenderer());

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
}
