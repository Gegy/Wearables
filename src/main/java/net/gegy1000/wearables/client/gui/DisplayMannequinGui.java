package net.gegy1000.wearables.client.gui;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.render.block.DisplayMannequinRenderer;
import net.gegy1000.wearables.server.block.entity.DisplayMannequinEntity;
import net.gegy1000.wearables.server.container.DisplayMannequinContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class DisplayMannequinGui extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/gui/display_mannequin.png");

    private final DisplayMannequinEntity entity;
    private final InventoryPlayer playerInventory;

    public DisplayMannequinGui(InventoryPlayer playerInventory, DisplayMannequinEntity entity) {
        super(new DisplayMannequinContainer(playerInventory, entity));
        this.entity = entity;
        this.playerInventory = playerInventory;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        this.drawMannequin(x + 88, y + 78, 28, partialTicks);
    }

    private void drawMannequin(int posX, int posY, int scale, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.translate(posX, posY, 50.0F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(scale, -scale, scale);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        TileEntitySpecialRenderer renderer = TileEntityRendererDispatcher.instance.getRenderer(this.entity);
        if (renderer instanceof DisplayMannequinRenderer) {
            ((DisplayMannequinRenderer) renderer).renderStatic(this.entity, partialTicks);
        }
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
