package net.gegy1000.wearables.server.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.gegy1000.wearables.server.block.BlockRegistry;
import net.gegy1000.wearables.server.item.ItemRegistry;
import net.gegy1000.wearables.server.item.WearableComponentItem;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class WearableJEIPlugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        IIngredientBlacklist ingredientBlacklist = registry.getJeiHelpers().getIngredientBlacklist();
        ingredientBlacklist.addIngredientToBlacklist(new ItemStack(BlockRegistry.DISPLAY_MANNEQUIN));
        ingredientBlacklist.addIngredientToBlacklist(new ItemStack(ItemRegistry.WEARABLE_HEAD));
        ingredientBlacklist.addIngredientToBlacklist(new ItemStack(ItemRegistry.WEARABLE_CHEST));
        ingredientBlacklist.addIngredientToBlacklist(new ItemStack(ItemRegistry.WEARABLE_LEGS));
        ingredientBlacklist.addIngredientToBlacklist(new ItemStack(ItemRegistry.WEARABLE_FEET));

        registry.addRecipeCatalyst(new ItemStack(BlockRegistry.WEARABLE_FABRICATOR), "wearables.fabricator");
        registry.handleRecipes(WearableComponentType.class, FabricatorRecipeWrapper::new, "wearables.fabricator");
        registry.addRecipes(ComponentRegistry.getRegistry().getValues(), "wearables.fabricator");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new FabricatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.WEARABLE_COMPONENT, stack -> {
            WearableComponentType type = WearableComponentItem.getComponent(stack).getType();
            return type.getRegistryName().getResourcePath();
        });
    }
}
