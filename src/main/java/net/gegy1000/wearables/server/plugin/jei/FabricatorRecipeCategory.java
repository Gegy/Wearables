package net.gegy1000.wearables.server.plugin.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.gegy1000.wearables.Wearables;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import java.util.List;

public class FabricatorRecipeCategory extends BlankRecipeCategory<IRecipeWrapper> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/gui/wearable_fabricator.png");

    private final IDrawable background;
    private final String title;

    public FabricatorRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 61, 7, 105, 72);
        this.title = I18n.translateToLocal("tile.wearable_fabricator.name");
    }

    @Override
    public String getUid() {
        return "wearables.fabricator";
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stackGroup = recipeLayout.getItemStacks();
        stackGroup.init(7, false, 83, 15);
        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        for (int column = 0; column < 4; column++) {
            stackGroup.init(column, true, column * 18, 54);
            if (column < inputs.size()) {
                stackGroup.set(column, inputs.get(column));
            }
        }
        stackGroup.set(7, ingredients.getOutputs(ItemStack.class).get(0));
    }
}
