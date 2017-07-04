package net.gegy1000.wearables.client.gui;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.render.ComponentRenderHandler;
import net.gegy1000.wearables.server.container.WearableAssemblerContainer;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.Wearable;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.ilexiconn.llibrary.LLibrary;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;

import java.io.IOException;

public class WearableAssemblerGui extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/gui/wearable_assembler.png");

    private final WearableAssemblerContainer container;
    private final BlockPos pos;
    private final InventoryPlayer playerInventory;

    public WearableAssemblerGui(InventoryPlayer playerInventory, BlockPos pos, WearableAssemblerContainer container) {
        super(container);
        this.container = container;
        this.pos = pos;
        this.playerInventory = playerInventory;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        if (mouseX >= x + 99 && mouseY >= y + 19 && mouseX <= x + 107 && mouseY <= y + 27) {
            this.drawTexturedModalRect(x + 99, y + 19, 176, 0, 9, 9);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = I18n.translateToLocal("tile.wearable_assembler.name");
        this.fontRenderer.drawString(title, this.xSize / 2 - this.fontRenderer.getStringWidth(title) / 2, 6, 0x404040);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 93, 0x404040);

        Slot slot = this.container.getSlot(7);
        ItemStack stack = slot.getStack();
        if (!stack.isEmpty() && stack.getItem() instanceof WearableItem) {
            Wearable wearable = WearableItem.getWearable(stack);

            float ticks = this.mc.player.ticksExisted + LLibrary.PROXY.getPartialTicks();
            GlStateManager.enableRescaleNormal();
            RenderHelper.enableStandardItemLighting();

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.translate(73.0F, 25.0F, 50.0F);
            GlStateManager.scale(-25.0F, 25.0F, 25.0F);
            GlStateManager.rotate(-20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(ticks % 360, 0.0F, 1.0F, 0.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.translate(0.0F, 0.8F, 0.0F);
            ComponentRenderHandler.fitSlot(WearableUtils.calculateUnion(wearable), 1.2);
            for (WearableComponent component : wearable.getComponents()) {
                ComponentRenderHandler.renderSingleComponent(component);
            }
            GlStateManager.popMatrix();

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        if (mouseX >= x + 99 && mouseY >= y + 19 && mouseX <= x + 107 && mouseY <= y + 27) {
            this.mc.displayGuiScreen(new WearableCustomizationGui(this, this.pos, this.container));
        }
    }
}
