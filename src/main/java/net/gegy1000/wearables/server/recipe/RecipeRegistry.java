package net.gegy1000.wearables.server.recipe;

import net.gegy1000.wearables.server.block.BlockRegistry;
import net.gegy1000.wearables.server.item.ItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeRegistry {
    public static void register() {
        GameRegistry.addRecipe(new ExtractArmourRecipe());
        GameRegistry.addRecipe(new ApplyArmourRecipe());

        GameRegistry.addRecipe(new ItemStack(ItemRegistry.DISPLAY_MANNEQUIN), " q ", "sqs", "wgw", 'q', Blocks.QUARTZ_BLOCK, 's', new ItemStack(Blocks.STONE_SLAB, 1, 7), 'g', Blocks.GLOWSTONE, 'w', new ItemStack(Blocks.WOODEN_SLAB, 1, 2));
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.HEAD_STAND_MANNEQUIN), " q ", "wqw", 'q', Blocks.QUARTZ_BLOCK, 'w', new ItemStack(Blocks.WOODEN_SLAB, 1, 2));

        GameRegistry.addRecipe(new ItemStack(BlockRegistry.WEARABLE_FABRICATOR), "ccc", "qiq", "qwq", 'q', Items.QUARTZ, 'c', Blocks.CARPET, 'w', Blocks.PLANKS, 'i', Items.CLAY_BALL);
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.WEARABLE_COLOURISER), "rgb", "qwq", "qiq", 'r', new ItemStack(Items.DYE, 1, EnumDyeColor.RED.getDyeDamage()), 'g', new ItemStack(Items.DYE, 1, EnumDyeColor.GREEN.getDyeDamage()), 'b', new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()), 'q', Items.QUARTZ, 'i', Items.IRON_INGOT, 'w', Items.GLASS_BOTTLE);
        GameRegistry.addRecipe(new ItemStack(BlockRegistry.WEARABLE_ASSEMBLER), " w ", "sws", "bbb", 'w', Blocks.PLANKS, 's', Items.STICK, 'b', Blocks.STONE_SLAB);

        GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.JETPACK_FUEL), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.GLASS_BOTTLE, 1));
    }
}

