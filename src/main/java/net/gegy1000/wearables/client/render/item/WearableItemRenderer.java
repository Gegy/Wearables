package net.gegy1000.wearables.client.render.item;

import net.gegy1000.wearables.client.render.ComponentRenderHandler;
import net.gegy1000.wearables.server.block.entity.wearable.WearableItemEntity;
import net.gegy1000.wearables.server.core.WearablesClientHooks;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.Wearable;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.AxisAlignedBB;

public class WearableItemRenderer extends TileEntitySpecialRenderer<WearableItemEntity> {
    @Override
    public void renderTileEntityAt(WearableItemEntity entity, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        Wearable wearable = WearableItem.getWearable(WearablesClientHooks.getRenderedStack());
        GlStateManager.scale(-1.0, -1.0, 1.0);
        AxisAlignedBB union = WearableUtils.calculateUnion(wearable);
        ComponentRenderHandler.fitSlot(union);
        for (WearableComponent component : wearable.getComponents()) {
            ComponentRenderHandler.renderSingleComponent(component);
        }
        GlStateManager.popMatrix();
    }
}
