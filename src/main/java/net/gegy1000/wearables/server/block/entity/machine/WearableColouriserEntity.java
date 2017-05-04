package net.gegy1000.wearables.server.block.entity.machine;

import net.gegy1000.wearables.server.item.WearableComponentItem;
import net.gegy1000.wearables.server.util.WearableColourUtils;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class WearableColouriserEntity extends InventoryBlockEntity {
    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("tile.wearable_colouriser.name");
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    public void setColour(int layer, int red, int green, int blue) {
        ItemStack stack = this.inventory.getStackInSlot(0);
        if (stack != null && stack.getItem() instanceof WearableComponentItem) {
            WearableComponent component = WearableComponentItem.getComponent(stack);
            component.setColour(layer, WearableColourUtils.fromRGB(red, green, blue));
        }
    }
}
