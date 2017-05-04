package net.gegy1000.wearables.client.gui;

import com.google.common.collect.Lists;
import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.render.ComponentRenderHandler;
import net.gegy1000.wearables.client.render.RenderRegistry;
import net.gegy1000.wearables.client.render.component.ComponentRenderer;
import net.gegy1000.wearables.server.block.entity.machine.WearableFabricatorEntity;
import net.gegy1000.wearables.server.container.WearableFabricatorContainer;
import net.gegy1000.wearables.server.item.ItemRegistry;
import net.gegy1000.wearables.server.network.SetSelectedComponentMessage;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.ilexiconn.llibrary.LLibrary;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;

import java.io.IOException;

public class WearableFabricatorGui extends GuiContainer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/gui/wearable_fabricator.png");

    private static final int SCROLLBAR_WIDTH = 8;
    private static final int SCROLLBAR_HEIGHT = 11;
    private static final int SCROLLBAR_OFFSET_X = 8;
    private static final int SCROLLBAR_OFFSET_Y = 8;
    private static final int SCROLL_LENGTH = 70;

    private final WearableFabricatorEntity entity;
    private final InventoryPlayer playerInventory;

    private final int maxScroll;

    private int scroll;
    private boolean scrolling;

    public WearableFabricatorGui(InventoryPlayer playerInventory, WearableFabricatorEntity entity) {
        super(new WearableFabricatorContainer(playerInventory, entity));
        this.entity = entity;
        this.playerInventory = playerInventory;
        this.maxScroll = Math.max(MathHelper.ceil(ComponentRegistry.COMPONENTS.size() / 2.0F) - 4, 0);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        RenderHelper.enableGUIStandardItemLighting();
        int componentX = 0;
        int componentY = -this.scroll;
        for (WearableComponentType componentType : ComponentRegistry.COMPONENTS) {
            int renderX = x + componentX * 18 + 22;
            int renderY = y + componentY * 18 + 8;
            if (componentY >= 0 && componentY < 4) {
                ItemStack stack = new ItemStack(ItemRegistry.WEARABLE_COMPONENT);
                stack.setTagCompound(new WearableComponent(componentType).serializeNBT());
                if (componentType == this.entity.getSelectedComponent()) {
                    GlStateManager.disableLighting();
                    this.mc.getTextureManager().bindTexture(TEXTURE);
                    this.drawTexturedModalRect(renderX - 1, renderY - 1, 192, 0, 18, 18);
                }
                this.itemRender.renderItemAndEffectIntoGUI(stack, renderX, renderY);
            }
            componentX++;
            if (componentX > 1) {
                componentX = 0;
                componentY++;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);

        int scrollbarX = SCROLLBAR_OFFSET_X;
        int scrollbarY = SCROLLBAR_OFFSET_Y + this.getScrollY();
        if (this.scrolling || this.maxScroll < 1) {
            this.drawTexturedModalRect(scrollbarX, scrollbarY, 184, 0, SCROLLBAR_WIDTH, SCROLLBAR_HEIGHT);
        } else {
            this.drawTexturedModalRect(scrollbarX, scrollbarY, 176, 0, SCROLLBAR_WIDTH, SCROLLBAR_HEIGHT);
        }

        int screenX = (this.width - this.xSize) / 2;
        int screenY = (this.height - this.ySize) / 2;

        if (this.entity.getSelectedComponent() != null) {
            ComponentRenderer renderer = RenderRegistry.getRenderer(this.entity.getSelectedComponent().getIdentifier());

            float ticks = this.mc.player.ticksExisted + LLibrary.PROXY.getPartialTicks();
            GlStateManager.enableRescaleNormal();
            RenderHelper.enableStandardItemLighting();

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.translate(88.0F, 20.0F, 50.0F);
            GlStateManager.scale(-30.0F, 30.0F, 30.0F);
            GlStateManager.rotate(-20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(ticks % 360, 0.0F, 1.0F, 0.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            ComponentRenderHandler.fitSlot(renderer.getBounds(), 1.0);
            ComponentRenderHandler.renderSingleComponent(new WearableComponent(this.entity.getSelectedComponent()));
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5, 0.5, 0.5);
            ItemStack[] ingredients = this.entity.getSelectedComponent().getIngredients();
            for (int i = 0; i < ingredients.length; i++) {
                ItemStack ingredient = ingredients[i];
                int x = 245;
                int y = 20 + i * 22;
                int offset = this.fontRendererObj.getStringWidth("x" + ingredient.stackSize);
                this.itemRender.renderItemAndEffectIntoGUI(ingredient, x - offset, y);
                this.fontRendererObj.drawString("x" + ingredient.stackSize, x + 16 - offset, y + 10, 0xFFFFFF);
            }
            GlStateManager.popMatrix();

            for (int i = 0; i < ingredients.length; i++) {
                ItemStack ingredient = ingredients[i];
                int x = 117 + screenX;
                int y = 10 + i * 11 + screenY;
                if (mouseX >= x && mouseY >= y && mouseX <= x + 11 && mouseY <= y + 11) {
                    this.renderToolTip(ingredient, mouseX - screenX, mouseY - screenY);
                    break;
                }
            }

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        }

        int componentX = 0;
        int componentY = -this.scroll;
        for (WearableComponentType componentType : ComponentRegistry.COMPONENTS) {
            int renderX = componentX * 18 + 22;
            int renderY = componentY * 18 + 8;
            if (componentY >= 0 && componentY < 4) {
                if (mouseX - screenX >= renderX && mouseY - screenY >= renderY && mouseX - screenX <= renderX + 17 && mouseY - screenY <= renderY + 17) {
                    GlStateManager.disableLighting();
                    GlStateManager.disableDepth();
                    GlStateManager.colorMask(true, true, true, false);
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
                    this.drawGradientRect(renderX, renderY, renderX + 16, renderY + 16, -2130706433, -2130706433);
                    GlStateManager.colorMask(true, true, true, true);
                    GlStateManager.enableLighting();
                    GlStateManager.enableDepth();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    this.drawHoveringText(Lists.newArrayList(I18n.translateToLocal("component." + componentType.getIdentifier() + ".name")), mouseX - screenX, mouseY - screenY);
                    break;
                }
            }
            componentX++;
            if (componentX > 1) {
                componentX = 0;
                componentY++;
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        int scrollbarX = x + SCROLLBAR_OFFSET_X;
        int scrollbarY = y + SCROLLBAR_OFFSET_Y + this.getScrollY();
        if (mouseX >= scrollbarX && mouseY >= scrollbarY && mouseX <= scrollbarX + SCROLLBAR_WIDTH && mouseY <= scrollbarY + SCROLLBAR_HEIGHT) {
            this.scrolling = true;
        }
        int componentX = 0;
        int componentY = -this.scroll;
        for (WearableComponentType componentType : ComponentRegistry.COMPONENTS) {
            if (componentY >= 0 && componentY < 4) {
                int renderX = x + componentX * 18 + 22;
                int renderY = y + componentY * 18 + 8;
                if (mouseX >= renderX && mouseY >= renderY && mouseX <= renderX + 17 && mouseY <= renderY + 17) {
                    Wearables.NETWORK_WRAPPER.sendToServer(new SetSelectedComponentMessage(this.entity.getPos(), componentType.getIdentifier()));
                    break;
                }
            }
            componentX++;
            if (componentX > 1) {
                componentX = 0;
                componentY++;
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        if (this.scrolling) {
            int scrollbarY = ((this.height - this.ySize) / 2) + SCROLLBAR_OFFSET_Y;
            this.scroll = MathHelper.clamp((int) ((mouseY - (scrollbarY + SCROLLBAR_HEIGHT / 2)) / (float) (SCROLL_LENGTH - SCROLLBAR_HEIGHT) * this.maxScroll), 0, this.maxScroll);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.scrolling = false;
    }

    private int getScrollY() {
        return (int) (this.maxScroll == 0 ? 0 : ((float) this.scroll / this.maxScroll * (SCROLL_LENGTH - SCROLLBAR_HEIGHT)));
    }
}
