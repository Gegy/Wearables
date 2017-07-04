package net.gegy1000.wearables.server.tab;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.ClientEventHandler;
import net.gegy1000.wearables.server.block.BlockRegistry;
import net.gegy1000.wearables.server.item.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class TabRegistry {
    public static final CreativeTabs GENERAL = new CreativeTabs(Wearables.MODID + ".general") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(BlockRegistry.WEARABLE_FABRICATOR);
        }
    };

    public static final CreativeTabs TEMPLATES = new CreativeTabs(Wearables.MODID + ".templates") {
        private NonNullList<ItemStack> subtypes;

        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemRegistry.WEARABLE_CHEST);
        }

        @Override
        public ItemStack getIconItemStack() {
            if (this.subtypes == null) {
                this.subtypes = NonNullList.create();
                ItemRegistry.WEARABLE_HEAD.getSubItems(this, this.subtypes);
                ItemRegistry.WEARABLE_CHEST.getSubItems(this, this.subtypes);
                ItemRegistry.WEARABLE_LEGS.getSubItems(this, this.subtypes);
                ItemRegistry.WEARABLE_FEET.getSubItems(this, this.subtypes);
            }
            return this.subtypes.get((ClientEventHandler.ticks / 20) % this.subtypes.size());
        }
    };
}
