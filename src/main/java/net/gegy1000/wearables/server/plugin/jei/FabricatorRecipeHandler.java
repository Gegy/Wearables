package net.gegy1000.wearables.server.plugin.jei;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;

public class FabricatorRecipeHandler implements IRecipeHandler<WearableComponentType> {
    @Override
    public Class<WearableComponentType> getRecipeClass() {
        return WearableComponentType.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return "wearables.fabricator";
    }

    @Override
    public String getRecipeCategoryUid(WearableComponentType recipe) {
        return this.getRecipeCategoryUid();
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(WearableComponentType recipe) {
        return new FabricatorRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(WearableComponentType recipe) {
        return true;
    }
}
