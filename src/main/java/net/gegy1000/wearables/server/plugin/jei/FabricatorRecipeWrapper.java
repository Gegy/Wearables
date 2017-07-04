package net.gegy1000.wearables.server.plugin.jei;

import com.google.common.collect.Lists;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.gegy1000.wearables.client.render.ComponentRenderHandler;
import net.gegy1000.wearables.client.render.RenderRegistry;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.gegy1000.wearables.server.item.ItemRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.ilexiconn.llibrary.LLibrary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class FabricatorRecipeWrapper implements IRecipeWrapper {
    private final WearableComponentType componentType;

    public FabricatorRecipeWrapper(WearableComponentType componentType) {
        this.componentType = componentType;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, Lists.newArrayList(this.componentType.getIngredients()));
        ItemStack output = new ItemStack(ItemRegistry.WEARABLE_COMPONENT);
        output.setTagCompound(new WearableComponent(this.componentType).serializeNBT());
        ingredients.setOutput(ItemStack.class, output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        ComponentRenderer renderer = RenderRegistry.getRenderer(this.componentType.getIdentifier());

        float ticks = minecraft.player.ticksExisted + LLibrary.PROXY.getPartialTicks();
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableStandardItemLighting();

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.translate(35.0F, 20.0F, 50.0F);
        GlStateManager.scale(-30.0F, 30.0F, 30.0F);
        GlStateManager.rotate(-20.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(ticks % 360, 0.0F, 1.0F, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        ComponentRenderHandler.fitSlot(renderer.getBounds(), 1.0);
        ComponentRenderHandler.renderSingleComponent(new WearableComponent(this.componentType));
        GlStateManager.popMatrix();

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.popMatrix();
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
