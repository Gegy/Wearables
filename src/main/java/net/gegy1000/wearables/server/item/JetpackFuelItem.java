package net.gegy1000.wearables.server.item;

import net.gegy1000.wearables.server.api.item.RegisterItemModel;
import net.gegy1000.wearables.server.tab.TabRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class JetpackFuelItem extends Item implements RegisterItemModel {
    public JetpackFuelItem() {
        super();
        this.setCreativeTab(TabRegistry.GENERAL);
        this.setMaxStackSize(1);
        this.setMaxDamage(200);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }
}
