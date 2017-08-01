package net.gegy1000.wearables.client.gui;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.render.ComponentRenderHandler;
import net.gegy1000.wearables.server.container.WearableColouriserContainer;
import net.gegy1000.wearables.server.container.slot.ColouredComponentSlot;
import net.gegy1000.wearables.server.item.WearableComponentItem;
import net.gegy1000.wearables.server.network.SetColourMessage;
import net.gegy1000.wearables.server.util.WearableColourUtils;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.ilexiconn.llibrary.client.util.ClientUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class WearableColouriserGui extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/gui/wearable_colouriser.png");

    private final BlockPos pos;
    private final WearableColouriserContainer container;

    private int selectedLayer;
    private boolean[] draggingSliders = new boolean[3];
    private int sliderOffsetX;

    private int switchBloom;
    private int prevSwitchBloom;

    private boolean hsv;
    private boolean bind;

    public WearableColouriserGui(InventoryPlayer playerInventory, BlockPos pos, WearableColouriserContainer container) {
        super(container);
        this.container = container;
        this.pos = pos;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.prevSwitchBloom = this.switchBloom;
        if (this.switchBloom > 0) {
            this.switchBloom--;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);

        GlStateManager.translate(0.0F, 0.0F, 300.0F);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        WearableComponent component = this.getComponent();
        if (component != null) {
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
            ComponentRenderHandler.fitSlot(component.getType().getBounds(), 1.25);
            ComponentRenderHandler.renderComponentLayerHighlighted(component, this.selectedLayer, ClientUtils.interpolate(this.prevSwitchBloom, this.switchBloom, partialTicks) / 10.0F);
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

        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        WearableComponentType.Layer[] layers = new WearableComponentType.Layer[0];

        WearableComponent component = this.getComponent();
        if (component != null) {
            layers = component.getType().getLayers(false);
        }

        for (int i = 0; i < 6; i++) {
            if (i >= layers.length) {
                this.drawTexturedModalRect(x + 7, y + 7 + i * 12, 176, 0, 36, 12);
            } else if (i == this.selectedLayer) {
                this.drawTexturedModalRect(x + 7, y + 7 + i * 12, 176, 12, 36, 12);
            } else if (component != null && !layers[i].canColour()) {
                this.drawTexturedModalRect(x + 7, y + 7 + i * 12, 176, 0, 36, 12);
            }
        }

        if (this.bind) {
            this.drawTexturedModalRect(x + 52, y + 7, 176, 34, 6, 6);
        }

        if (this.hsv) {
            this.drawTexturedModalRect(x + 52, y + 73, 176, 34, 6, 6);
        }

        this.fontRenderer.drawString(I18n.translateToLocal("label.wearable_bind.name"), x + 62, y + 6, 0);
        this.fontRenderer.drawString(I18n.translateToLocal("label.wearable_hsv.name"), x + 62, y + 72, 0);

        if (component != null) {
            for (int i = 0; i < layers.length; i++) {
                if (layers[i].canColour()) {
                    this.fontRenderer.drawString("#" + (i + 1), x + 9, y + 10 + i * 12, 0);
                }
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);

        int colour = 0xFFFFFF;

        int channel1 = 255;
        int channel2 = 255;
        int channel3 = 255;

        WearableComponent component = this.getComponent();
        if (component != null) {
            colour = component.getColour(this.selectedLayer);
            if (this.hsv) {
                float[] hsv = WearableColourUtils.toHSVFloatArray(colour);
                channel1 = (int) (hsv[0] * 255);
                channel2 = (int) (hsv[1] * 255);
                channel3 = (int) (hsv[2] * 255);
            } else {
                channel1 = colour >> 16 & 0xFF;
                channel2 = colour >> 8 & 0xFF;
                channel3 = colour & 0xFF;
            }
        }

        int redX = (int) (channel1 / 255.0F * 43);
        int greenX = (int) (channel2 / 255.0F * 43);
        int blueX = (int) (channel3 / 255.0F * 43);

        if (this.hsv) {
            this.drawHorizontalHSVChannelGradientRect(111, 19, 163, 29, colour, 0);
            this.drawHorizontalHSVChannelGradientRect(111, 38, 163, 48, colour, 1);
            this.drawHorizontalHSVChannelGradientRect(111, 57, 163, 67, colour, 2);
        } else {
            this.drawHorizontalChannelMaskGradientRect(111, 19, 163, 29, colour, 0xFF0000);
            this.drawHorizontalChannelMaskGradientRect(111, 38, 163, 48, colour, 0x00FF00);
            this.drawHorizontalChannelMaskGradientRect(111, 57, 163, 67, colour, 0x0000FF);
        }

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
        if (mouseX >= x + 52 && mouseY >= y + 7 && mouseX <= x + 58 && mouseY <= y + 13) {
            this.bind = !this.bind;
        }
        if (mouseX >= x + 52 && mouseY >= y + 73 && mouseX <= x + 58 && mouseY <= y + 79) {
            this.hsv = !this.hsv;
        }
        WearableComponent component = this.getComponent();
        if (component != null) {
            WearableComponentType.Layer[] layers = component.getType().getLayers(false);
            for (int i = 0; i < layers.length; i++) {
                if (layers[i].canColour()) {
                    if (mouseX >= x + 7 && mouseY >= y + 7 + i * 12 && mouseX <= x + 43 && mouseY <= y + 19 + i * 12) {
                        this.selectedLayer = i;
                        this.switchBloom = 10;
                        return;
                    }
                }
            }
            int colour = component.getColour(this.selectedLayer);
            int channel1;
            int channel2;
            int channel3;
            if (this.hsv) {
                float[] hsv = WearableColourUtils.toHSVFloatArray(colour);
                channel1 = (int) (hsv[0] * 255);
                channel2 = (int) (hsv[1] * 255);
                channel3 = (int) (hsv[2] * 255);
            } else {
                channel1 = colour >> 16 & 0xFF;
                channel2 = colour >> 8 & 0xFF;
                channel3 = colour & 0xFF;
            }
            int redX = (int) (channel1 / 255.0F * 43);
            int greenX = (int) (channel2 / 255.0F * 43);
            int blueX = (int) (channel3 / 255.0F * 43);
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
                    this.sliderOffsetX = mouseX - sliderX;
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
                if (stack.getItem() instanceof WearableComponentItem) {
                    component = WearableComponentItem.getComponent(stack);
                }
                if (component != null) {
                    int value = MathHelper.clamp((int) ((mouseX - (sliderX + this.sliderOffsetX)) / (float) (52 - 9) * 255), 0, 255);
                    int colour = this.updateColour(i, component.getColour(this.selectedLayer), value);
                    if (this.bind) {
                        WearableComponentType.Layer[] layers = component.getType().getLayers(false);
                        for (int layer = 0; layer < layers.length; layer++) {
                            if (layers[layer].canColour()) {
                                component.setColour(layer, colour);
                            }
                        }
                    } else {
                        component.setColour(this.selectedLayer, colour);
                    }
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
            Wearables.NETWORK_WRAPPER.sendToServer(new SetColourMessage(this.pos, this.selectedLayer, component.getColour(this.selectedLayer)));
        }
    }

    private int updateColour(int index, int colour, int value) {
        if (this.hsv) {
            float[] hsv = WearableColourUtils.toHSVFloatArray(colour);
            hsv[index] = value / 255.0F;
            return WearableColourUtils.fromHSVFloatArray(hsv);
        } else {
            if (index == 0) {
                return (colour & ~0xFF0000) | value << 16;
            } else if (index == 1) {
                return (colour & ~0xFF00) | value << 8;
            } else if (index == 2) {
                return (colour & ~0xFF) | value;
            }
        }
        return colour;
    }

    private ItemStack getComponentStack() {
        Slot slot = this.container.getSlot(0);
        return slot.getStack();
    }

    private WearableComponent getComponent() {
        ItemStack stack = this.getComponentStack();
        if (stack.getItem() instanceof WearableComponentItem) {
            return WearableComponentItem.getComponent(stack);
        }
        return null;
    }

    protected void drawHorizontalHSVChannelGradientRect(int left, int top, int right, int bottom, int colour, int channel) {
        float[] hsv = WearableColourUtils.toHSVFloatArray(colour);
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
        int width = right - left;
        for (int i = 0; i <= 255; i++) {
            float scaled = i / 255.0F;
            float offset = scaled * width;
            hsv[channel] = scaled;
            float[] rgb = WearableColourUtils.toRGBFloatArray(WearableColourUtils.fromHSVFloatArray(hsv));
            buffer.pos(left + offset, top, this.zLevel).color(rgb[0], rgb[1], rgb[2], 1.0F).endVertex();
            buffer.pos(left + offset, bottom, this.zLevel).color(rgb[0], rgb[1], rgb[2], 1.0F).endVertex();
        }
        tessellator.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
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
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(right, top, this.zLevel).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        buffer.pos(left, top, this.zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(left, bottom, this.zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos(right, bottom, this.zLevel).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
