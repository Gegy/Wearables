package net.gegy1000.wearables.client.gui;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.render.ComponentProperty;
import net.gegy1000.wearables.server.container.WearableAssemblerContainer;
import net.gegy1000.wearables.server.item.WearableComponentItem;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.network.SetPropertyMessage;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;

import java.io.IOException;
import java.text.DecimalFormat;

public class WearableCustomizationGui extends GuiScreen {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/gui/wearable_customization.png");

    private final WearableAssemblerGui parent;
    private final BlockPos pos;
    private final WearableAssemblerContainer container;
    private final int xSize = 176;
    private final int ySize = 166;

    private EntityPlayer fakePlayer;

    private float rotation;

    private boolean closed;

    private int selectedComponent = -1;

    public WearableCustomizationGui(WearableAssemblerGui parent, BlockPos pos, WearableAssemblerContainer container) {
        this.parent = parent;
        this.pos = pos;
        this.container = container;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.fakePlayer = new EntityOtherPlayerMP(this.mc.world, this.mc.getSession().getProfile()) {
            @Override
            public boolean isWearing(EnumPlayerModelParts part) {
                return true;
            }
        };
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (int componentIndex = 0; componentIndex < 6; componentIndex++) {
            ItemStack stack = this.container.getSlot(componentIndex).getStack();
            if (stack != null && stack.getItem() instanceof WearableComponentItem) {
                if (this.selectedComponent == componentIndex) {
                    this.drawTexturedModalRect(x + 147, y + 26 + componentIndex * 21, 198, 0, 18, 18);
                }
                RenderHelper.enableGUIStandardItemLighting();
                this.itemRender.renderItemAndEffectIntoGUI(stack, x + 148, y + 27 + componentIndex * 21);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableLighting();
                this.mc.getTextureManager().bindTexture(TEXTURE);
            } else {
                this.drawTexturedModalRect(x + 147, y + 26 + componentIndex * 21, 216, 0, 18, 18);
            }
        }
        for (int componentIndex = 0; componentIndex < 6; componentIndex++) {
            ItemStack stack = this.container.getSlot(componentIndex).getStack();
            if (stack != null && stack.getItem() instanceof WearableComponentItem) {
                if (mouseX >= x + 147 && mouseY >= y + 26 + componentIndex * 21 && mouseX <= x + 165 && mouseY < y + 44 + componentIndex * 21) {
                    if (this.selectedComponent != componentIndex) {
                        GlStateManager.disableLighting();
                        GlStateManager.disableDepth();
                        GlStateManager.colorMask(true, true, true, false);
                        this.drawGradientRect(x + 148, y + 27 + componentIndex * 21, x + 165, y + 44 + componentIndex * 21, 0x80FFFFFF, 0x80FFFFFF);
                        GlStateManager.colorMask(true, true, true, true);
                        GlStateManager.enableDepth();
                    }
                    this.renderToolTip(stack, mouseX, mouseY);
                    break;
                }
            }
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();

        this.drawPropertyWidgets(x + 39, y + 34, mouseX, mouseY, ComponentProperty.OFFSET_Y);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);

        if (mouseX >= x + 67 && mouseY >= y + 140 && mouseX <= x + 77 && mouseY <= y + 150) {
            this.drawTexturedModalRect(x + 67, y + 140, 176, 14, 11, 11);
        }
        if (mouseX >= x + 129 && mouseY >= y + 140 && mouseX <= x + 140 && mouseY <= y + 150) {
            this.drawTexturedModalRect(x + 129, y + 140, 187, 14, 11, 11);
        }

        String title = I18n.translateToLocal("label.wearable_customization.name");
        this.fontRendererObj.drawString(title, x + this.xSize / 2 - this.fontRendererObj.getStringWidth(title) / 2, y + 6, 0x404040);
        this.fontRendererObj.drawString(I18n.translateToLocal("label.wearable_height.name"), x + 6, y + 35, 0x404040);

        this.fakePlayer.renderYawOffset = this.rotation;
        this.fakePlayer.rotationYaw = this.rotation;
        this.fakePlayer.rotationPitch = 0.0F;
        this.fakePlayer.rotationYawHead = this.fakePlayer.rotationYaw;
        this.fakePlayer.prevRotationYawHead = this.fakePlayer.rotationYaw;
        ItemStack result = this.container.getResult();
        if (result == null) {
            for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
                this.fakePlayer.setItemStackToSlot(slot, null);
            }
        } else if (result.getItem() instanceof WearableItem) {
            this.fakePlayer.setItemStackToSlot(((WearableItem) result.getItem()).getEquipmentSlot(), result);
        }

        GlStateManager.enableColorMaterial();
        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 104, y + 130, 50.0F);
        GlStateManager.scale(-43.0F, 43.0F, 43.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        RenderManager renderManager = this.mc.getRenderManager();
        renderManager.setPlayerViewY(180.0F);
        renderManager.setRenderShadow(false);
        renderManager.doRenderEntity(this.fakePlayer, 0.0, 0.0, 0.0, 0.0F, 1.0F, false);
        renderManager.setRenderShadow(true);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        for (int componentIndex = 0; componentIndex < 6; componentIndex++) {
            ItemStack stack = this.container.getSlot(componentIndex).getStack();
            if (stack != null && stack.getItem() instanceof WearableComponentItem) {
                if (mouseX >= x + 147 && mouseY >= y + 26 + componentIndex * 21 && mouseX <= x + 165 && mouseY < y + 44 + componentIndex * 21) {
                    this.selectedComponent = componentIndex;
                    break;
                }
            }
        }
        this.clickPropertyWidget(x + 39, y + 34, mouseX, mouseY, ComponentProperty.OFFSET_Y);
        if (mouseX >= x + 67 && mouseY >= y + 140 && mouseX <= x + 77 && mouseY <= y + 150) {
            this.rotation += 10.0F;
        }
        if (mouseX >= x + 129 && mouseY >= y + 140 && mouseX <= x + 140 && mouseY <= y + 150) {
            this.rotation -= 10.0F;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.onGuiClosed();
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void onGuiClosed() {
        if (!this.closed) {
            this.closed = true;
            this.mc.displayGuiScreen(this.parent);
        }
    }

    private void drawPropertyWidgets(int renderX, int renderY, int mouseX, int mouseY, int property) {
        this.mc.getTextureManager().bindTexture(TEXTURE);

        boolean canDecrement = false;
        boolean canIncrement = false;

        float value = 0.0F;

        if (this.selectedComponent != -1) {
            ItemStack stack = this.container.getSlot(this.selectedComponent).getStack();
            if (stack != null && stack.getItem() instanceof WearableComponentItem) {
                WearableComponent component = WearableComponentItem.getComponent(stack);
                WearableComponentType type = component.getType();
                if ((type.getSupportedProperties() & property) != 0) {
                    value = component.getProperty(property);
                    canDecrement = value - 0.1F >= type.getMinimum(property);
                    canIncrement = value + 0.1F <= type.getMaximum(property);
                }
            }
        }

        if (!canIncrement) {
            this.drawTexturedModalRect(renderX + 6, renderY - 8, 187, 0, 11, 7);
        } else {
            if (mouseX >= renderX + 6 && mouseY >= renderY - 8 && mouseX <= renderX + 16 && mouseY <= renderY - 3) {
                this.drawTexturedModalRect(renderX + 6, renderY - 8, 176, 0, 11, 7);
            }
        }
        if (!canDecrement) {
            this.drawTexturedModalRect(renderX + 6, renderY + 11, 187, 7, 11, 7);
        } else {
            if (mouseX >= renderX + 6 && mouseY >= renderY + 11 && mouseX <= renderX + 16 && mouseY <= renderY + 18) {
                this.drawTexturedModalRect(renderX + 6, renderY + 11, 176, 7, 11, 7);
            }
        }

        if (canDecrement || canIncrement) {
            String valueString = new DecimalFormat("#.#").format(value);
            this.fontRendererObj.drawString(valueString, renderX + 12 - this.fontRendererObj.getStringWidth(valueString) / 2, renderY + 1.5F, 0x404040, false);
        }
    }

    private void clickPropertyWidget(int renderX, int renderY, int mouseX, int mouseY, int property) {
        if (this.selectedComponent != -1) {
            ItemStack stack = this.container.getSlot(this.selectedComponent).getStack();
            if (stack != null && stack.getItem() instanceof WearableComponentItem) {
                WearableComponent component = WearableComponentItem.getComponent(stack);
                WearableComponentType type = component.getType();
                if ((type.getSupportedProperties() & property) != 0) {
                    float value = component.getProperty(property);
                    boolean canDecrement = value - 0.1F >= type.getMinimum(property);
                    boolean canIncrement = value + 0.1F <= type.getMaximum(property);
                    if (canIncrement) {
                        if (mouseX >= renderX + 6 && mouseY >= renderY - 8 && mouseX <= renderX + 16 && mouseY <= renderY - 3) {
                            component.setProperty(property, value + 0.1F);
                            Wearables.NETWORK_WRAPPER.sendToServer(new SetPropertyMessage(this.pos, this.selectedComponent, property, value + 0.1F));
                        }
                    }
                    if (canDecrement) {
                        if (mouseX >= renderX + 6 && mouseY >= renderY + 11 && mouseX <= renderX + 16 && mouseY <= renderY + 18) {
                            component.setProperty(property, value - 0.1F);
                            Wearables.NETWORK_WRAPPER.sendToServer(new SetPropertyMessage(this.pos, this.selectedComponent, property, value - 0.1F));
                        }
                    }
                    stack.setTagCompound(component.serializeNBT());
                    this.container.onContentsChanged();
                }
            }
        }
    }
}
