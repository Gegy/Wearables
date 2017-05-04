package net.gegy1000.wearables.client.gui;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.render.ComponentRenderHandler;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.gegy1000.wearables.server.container.WearableColouriserContainer;
import net.gegy1000.wearables.server.container.slot.ColouredComponentSlot;
import net.gegy1000.wearables.server.item.WearableComponentItem;
import net.gegy1000.wearables.server.network.SetColourMessage;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class WearableColouriserGui extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/gui/wearable_colouriser.png");

    private final BlockPos pos;
    private final WearableColouriserContainer container;

    private int selectedLayer;
    private boolean[] draggingSliders = new boolean[3];

    public WearableColouriserGui(InventoryPlayer playerInventory, BlockPos pos, WearableColouriserContainer container) {
        super(container);
        this.container = container;
        this.pos = pos;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.translate(0.0F, 0.0F, 300.0F);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        WearableComponent component = this.getComponent();
        if (component != null) {
            ComponentRenderer renderer = WearableUtils.getRenderer(component);

            GlStateManager.enableRescaleNormal();
            RenderHelper.enableGUIStandardItemLighting();

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.translate(x + 78.0F, y + 35.0F, 50.0F);
            GlStateManager.scale(-30.0F, 30.0F, 30.0F);
            GlStateManager.rotate(-30.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-45.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            ComponentRenderHandler.fitSlot(renderer.getBounds(), 1.0);
            ComponentRenderHandler.renderComponent(component, false);
            GlStateManager.popMatrix();

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

            if (mouseX >= x + 52 && mouseY >= y + 19 && mouseX <= x + 101 && mouseY <= y + 68) {
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.colorMask(true, true, true, false);
                this.drawGradientRect(x + 53, y + 19, x + 102, y + 68, 0x20FFFFFF, 0x20FFFFFF);
                GlStateManager.colorMask(true, true, true, true);
                GlStateManager.enableDepth();
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        int layerCount = 0;
        WearableComponent component = this.getComponent();
        if (component != null) {
            layerCount = component.getType().getLayerCount();
        }
        for (int i = 0; i < 6; i++) {
            if (i >= layerCount) {
                this.drawTexturedModalRect(x + 7, y + 7 + i * 12, 176, 0, 36, 12);
            } else if (i == this.selectedLayer) {
                this.drawTexturedModalRect(x + 7, y + 7 + i * 12, 176, 12, 36, 12);
            } else if (component != null && !component.getType().canColour(i)) {
                this.drawTexturedModalRect(x + 7, y + 7 + i * 12, 176, 0, 36, 12);
            }
        }
        if (component != null) {
            GlStateManager.pushMatrix();
            for (int i = 0; i < layerCount; i++) {
                if (component.getType().canColour(i)) {
                    this.fontRendererObj.drawString("#" + (i + 1), x + 9, y + 10 + i * 12, 0);
                }
            }
            GlStateManager.popMatrix();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);

        int colour = 0xFFFFFF;

        int red = 255;
        int green = 255;
        int blue = 255;

        WearableComponent component = this.getComponent();
        if (component != null) {
            colour = component.getColour(this.selectedLayer);
            red = colour >> 16 & 0xFF;
            green = colour >> 8 & 0xFF;
            blue = colour & 0xFF;
        }

        int redX = (int) (red / 255.0F * 43);
        int greenX = (int) (green / 255.0F * 43);
        int blueX = (int) (blue / 255.0F * 43);

        this.drawHorizontalChannelMaskGradientRect(111, 19, 163, 29, colour, 0xFF0000);
        this.drawHorizontalChannelMaskGradientRect(111, 38, 163, 48, colour, 0x00FF00);
        this.drawHorizontalChannelMaskGradientRect(111, 57, 163, 67, colour, 0x0000FF);

        this.drawTexturedModalRect(111 + redX, 19, this.draggingSliders[0] ? 185 : 176, 24, 9, 10);
        this.drawTexturedModalRect(111 + greenX, 38, this.draggingSliders[1] ? 185 : 176, 24, 9, 10);
        this.drawTexturedModalRect(111 + blueX, 57, this.draggingSliders[2] ? 185 : 176, 24, 9, 10);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        if (mouseX >= x + 52 && mouseY >= y + 19 && mouseX <= x + 101 && mouseY <= y + 68) {
            Slot slot = this.container.getSlot(0);
            if (slot instanceof ColouredComponentSlot) {
                ((ColouredComponentSlot) slot).setEnabled(true);
            }
            this.selectedLayer = 0;
            super.mouseClicked(x + 69, y + 35, mouseButton);
            if (slot instanceof ColouredComponentSlot) {
                ((ColouredComponentSlot) slot).setEnabled(false);
            }
        } else {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        WearableComponent component = this.getComponent();
        if (component != null) {
            for (int i = 0; i < component.getType().getLayerCount(); i++) {
                if (component.getType().canColour(i)) {
                    if (mouseX >= x + 7 && mouseY >= y + 7 + i * 12 && mouseX <= x + 43 && mouseY <= y + 19 + i * 12) {
                        this.selectedLayer = i;
                    }
                }
            }
            int colour = component.getColour(this.selectedLayer);
            int red = colour >> 16 & 0xFF;
            int green = colour >> 8 & 0xFF;
            int blue = colour & 0xFF;
            int redX = (int) (red / 255.0F * 43);
            int greenX = (int) (green / 255.0F * 43);
            int blueX = (int) (blue / 255.0F * 43);
            for (int i = 0; i < this.draggingSliders.length; i++) {
                int offsetX = 0;
                if (i == 0) {
                    offsetX = redX;
                } else if (i == 1) {
                    offsetX = greenX;
                } else if (i == 2) {
                    offsetX = blueX;
                }
                int sliderX = x + 111 + offsetX;
                int sliderY = y + 19 + i * 20;
                if (mouseX >= sliderX && mouseY >= sliderY && mouseX <= sliderX + 9 && mouseY <= sliderY + 10) {
                    this.draggingSliders[i] = true;
                }
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        int x = (this.width - this.xSize) / 2;
        for (int i = 0; i < this.draggingSliders.length; i++) {
            if (this.draggingSliders[i]) {
                int sliderX = x + 111;
                WearableComponent component = null;
                ItemStack stack = this.getComponentStack();
                if (stack != null && stack.getItem() instanceof WearableComponentItem) {
                    component = WearableComponentItem.getComponent(stack);
                }
                if (component != null) {
                    int value = MathHelper.clamp((int) ((mouseX - (sliderX + 4)) / (float) (52 - 9) * 255), 0, 255);
                    int colour = component.getColour(this.selectedLayer);
                    if (i == 0) {
                        colour = (colour & ~0xFF0000) | value << 16;
                    } else if (i == 1) {
                        colour = (colour & ~0xFF00) | value << 8;
                    } else if (i == 2) {
                        colour = (colour & ~0xFF) | value;
                    }
                    component.setColour(this.selectedLayer, colour);
                }
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        boolean changed = false;
        for (int i = 0; i < this.draggingSliders.length; i++) {
            if (this.draggingSliders[i]) {
                changed = true;
            }
            this.draggingSliders[i] = false;
        }
        WearableComponent component = this.getComponent();
        if (component != null && changed) {
            int colour = component.getColour(this.selectedLayer);
            int red = colour >> 16 & 0xFF;
            int green = colour >> 8 & 0xFF;
            int blue = colour & 0xFF;
            Wearables.NETWORK_WRAPPER.sendToServer(new SetColourMessage(this.pos, this.selectedLayer, red, green, blue));
        }
    }

    private ItemStack getComponentStack() {
        Slot slot = this.container.getSlot(0);
        return slot.getStack();
    }

    private WearableComponent getComponent() {
        ItemStack stack = this.getComponentStack();
        if (stack != null && stack.getItem() instanceof WearableComponentItem) {
            return WearableComponentItem.getComponent(stack);
        }
        return null;
    }

    private void drawHorizontalChannelMaskGradientRect(int left, int top, int right, int bottom, int colour, int mask) {
        this.drawHorizontalGradientRect(left, top, right, bottom, (colour & ~mask) | 0xFF000000, colour | 0xFF000000 | mask);
    }

    protected void drawHorizontalGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float startAlpha = (float) (startColor >> 24 & 255) / 255.0F;
        float startRed = (float) (startColor >> 16 & 255) / 255.0F;
        float startGreen = (float) (startColor >> 8 & 255) / 255.0F;
        float startBlue = (float) (startColor & 255) / 255.0F;
        float endAlpha = (float) (endColor >> 24 & 255) / 255.0F;
        float endRed = (float) (endColor >> 16 & 255) / 255.0F;
        float endGreen = (float) (endColor >> 8 & 255) / 255.0F;
        float endBlue = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(right, top, this.zLevel).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        buffer.pos(left, top, this.zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(left, bottom, this.zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(right, bottom, this.zLevel).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
