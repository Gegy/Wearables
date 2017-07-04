package net.gegy1000.wearables.server.block;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.api.item.RegisterBlockEntity;
import net.gegy1000.wearables.server.api.item.RegisterItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Wearables.MODID)
@GameRegistry.ObjectHolder(Wearables.MODID)
public class BlockRegistry {
    private static final Set<Block> BLOCKS = new LinkedHashSet<>();
    private static final Set<Block> IMMUTABLE_BLOCKS = Collections.unmodifiableSet(BLOCKS);

    private static final Block EMPTY = new Block(Material.AIR);

    public static final Block WEARABLE_FABRICATOR = EMPTY;
    public static final Block WEARABLE_COLOURISER = EMPTY;
    public static final Block WEARABLE_ASSEMBLER = EMPTY;
    public static final Block DISPLAY_MANNEQUIN = EMPTY;
    public static final Block MANNEQUIN_HEAD_STAND = EMPTY;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BLOCKS.clear();

        IForgeRegistry<Block> registry = event.getRegistry();

        BlockRegistry.register(registry, new WearableFabricatorBlock(), new ResourceLocation(Wearables.MODID, "wearable_fabricator"));
        BlockRegistry.register(registry, new WearableColouriserBlock(), new ResourceLocation(Wearables.MODID, "wearable_colouriser"));
        BlockRegistry.register(registry, new WearableAssemblerBlock(), new ResourceLocation(Wearables.MODID, "wearable_assembler"));
        BlockRegistry.register(registry, new DisplayMannequinBlock(), new ResourceLocation(Wearables.MODID, "display_mannequin"));
        BlockRegistry.register(registry, new MannequinHeadStandBlock(), new ResourceLocation(Wearables.MODID, "mannequin_head_stand"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        for (Block block : BLOCKS) {
            ItemBlock item = BlockRegistry.createItemBlock(block);

            if (item != null) {
                item.setRegistryName(block.getRegistryName());
                registry.register(item);
            }
        }
    }

    private static void register(IForgeRegistry<Block> registry, Block block, ResourceLocation identifier) {
        BLOCKS.add(block);

        block.setRegistryName(identifier);
        block.setUnlocalizedName(identifier.getResourcePath());

        registry.register(block);

        if (block instanceof RegisterBlockEntity) {
            GameRegistry.registerTileEntity(((RegisterBlockEntity) block).getEntity(), Wearables.MODID + ":" + identifier.getResourcePath());
        }
    }

    private static ItemBlock createItemBlock(Block block) {
        if (block instanceof RegisterItemBlock) {
            return ((RegisterItemBlock) block).createItemBlock();
        }
        return new ItemBlock(block);
    }

    public static Set<? extends Block> getBlocks() {
        return IMMUTABLE_BLOCKS;
    }
}
