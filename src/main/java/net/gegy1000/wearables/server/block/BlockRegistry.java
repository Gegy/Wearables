package net.gegy1000.wearables.server.block;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.api.item.RegisterBlockEntity;
import net.gegy1000.wearables.server.api.item.RegisterItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BlockRegistry {
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final WearableFabricatorBlock WEARABLE_FABRICATOR = new WearableFabricatorBlock();
    public static final WearableColouriserBlock WEARABLE_COLOURISER = new WearableColouriserBlock();
    public static final WearableAssemblerBlock WEARABLE_ASSEMBLER = new WearableAssemblerBlock();

    public static final DisplayMannequinBlock DISPLAY_MANNEQUIN = new DisplayMannequinBlock();
    public static final MannequinHeadStandBlock HEAD_STAND_MANNEQUIN = new MannequinHeadStandBlock();

    public static void register() {
        try {
            for (Field field : BlockRegistry.class.getDeclaredFields()) {
                Object value = field.get(null);
                if (value instanceof Block) {
                    BlockRegistry.register((Block) value);
                } else if (value instanceof Block[]) {
                    for (Block block : (Block[]) value) {
                        BlockRegistry.register(block);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void register(Block block) {
        BLOCKS.add(block);
        String name = block.getUnlocalizedName().substring("tile.".length());
        ResourceLocation identifier = new ResourceLocation(Wearables.MODID, name);
        GameRegistry.register(block, identifier);
        GameRegistry.register(BlockRegistry.createItemBlock(block), identifier);
        if (block instanceof RegisterBlockEntity) {
            GameRegistry.registerTileEntity(((RegisterBlockEntity) block).getEntity(), Wearables.MODID + ":" + name);
        }
    }

    private static ItemBlock createItemBlock(Block block) {
        if (block instanceof RegisterItemBlock) {
            return ((RegisterItemBlock) block).createItemBlock();
        }
        return new ItemBlock(block);
    }
}
