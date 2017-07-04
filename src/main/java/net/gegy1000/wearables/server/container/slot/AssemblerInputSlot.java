package net.gegy1000.wearables.server.container.slot;

import net.gegy1000.wearables.server.container.WearableAssemblerContainer;
import net.gegy1000.wearables.server.item.WearableComponentItem;
import net.gegy1000.wearables.server.util.WearableUtils;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AssemblerInputSlot extends SlotItemHandler {
    private WearableAssemblerContainer container;

    public AssemblerInputSlot(WearableAssemblerContainer container, IItemHandler inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.container = container;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (!WearableUtils.isStackEmpty(stack) && stack.getItem() instanceof WearableComponentItem) {
            WearableComponent component = WearableComponentItem.getComponent(stack);
            return this.container.canAddComponent(component);
        }
        return false;
    }
}
