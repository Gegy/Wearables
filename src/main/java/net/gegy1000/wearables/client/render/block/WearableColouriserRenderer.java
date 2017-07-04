package net.gegy1000.wearables.client.render.block;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.block.ColouriserModel;
import net.gegy1000.wearables.client.render.ComponentRenderHandler;
import net.gegy1000.wearables.server.block.WearableColouriserBlock;
import net.gegy1000.wearables.server.block.entity.machine.WearableColouriserEntity;
import net.gegy1000.wearables.server.item.WearableComponentItem;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class WearableColouriserRenderer extends TileEntitySpecialRenderer<WearableColouriserEntity> {
    private static final Minecraft MC = Minecraft.getMinecraft();

    private static final ColouriserModel MODEL = new ColouriserModel();
    private static final ResourceLocation TEXTURE = new ResourceLocation(Wearables.MODID, "textures/blocks/wearable_colouriser.png");

    @Override
    public void render(WearableColouriserEntity entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        EnumFacing facing = EnumFacing.SOUTH;

        if (entity != null) {
            BlockPos pos = entity.getPos();
            IBlockState state = entity.getWorld().getBlockState(pos);
            if (state.getBlock() instanceof WearableColouriserBlock) {
                facing = state.getValue(WearableColouriserBlock.FACING);
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 1.5F, z + 0.5);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.rotate(facing.getHorizontalAngle() - 180.0F, 0.0F, 1.0F, 0.0F);

        MC.getTextureManager().bindTexture(TEXTURE);
        MODEL.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        if (entity != null) {
            IItemHandler inventory = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            ItemStack stack = inventory.getStackInSlot(0);
            GlStateManager.enableRescaleNormal();
            if (!WearableUtils.isStackEmpty(stack) && stack.getItem() instanceof WearableComponentItem) {
                WearableComponent component = WearableComponentItem.getComponent(stack);
                GlStateManager.pushMatrix();
                GlStateManager.translate(-0.05F, 0.8F, 0.05F);
                GlStateManager.scale(0.45F, 0.45F, 0.45F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                ComponentRenderHandler.fitSlot(component.getType().getBounds(), 1.2);
                ComponentRenderHandler.renderSingleComponent(component);
                GlStateManager.popMatrix();
            }
        }

        GlStateManager.popMatrix();
    }
}
