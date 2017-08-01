package net.gegy1000.wearables.server.item;

import net.gegy1000.wearables.server.api.item.RegisterItemModel;
import net.gegy1000.wearables.server.tab.TabRegistry;
import net.gegy1000.wearables.server.util.ComponentTagCompound;
import net.gegy1000.wearables.server.util.WearableColourUtils;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;

public class WearableComponentItem extends Item implements RegisterItemModel {
    public WearableComponentItem() {
        super();
        this.setCreativeTab(TabRegistry.GENERAL);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        WearableComponent component = WearableComponentItem.getComponent(stack);
        String identifier = component.getType().getRegistryName().getResourcePath();
        return I18n.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".name", I18n.translateToLocal("component." + identifier + ".name"));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (WearableComponentType componentType : ComponentRegistry.getRegistry()) {
                WearableComponent component = new WearableComponent(componentType);
                ItemStack stack = new ItemStack(this);
                stack.setTagCompound(component.serializeNBT());
                items.add(stack);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        WearableComponent component = WearableComponentItem.getComponent(stack);
        if (component.getType().hasTooltip()) {
            String identifier = component.getType().getRegistryName().getResourcePath();
            tooltip.add(TextFormatting.BOLD + I18n.translateToLocal("tooltip.wearable." + identifier + ".name"));
        }
        tooltip.add(I18n.translateToLocal("label.wearable_layers.name"));
        int layer = 1;
        WearableComponentType.Layer[] layers = component.getType().getLayers(false);
        for (int i = 0; i < layers.length; i++) {
            if (layers[i].canColour()) {
                int colour = component.getColour(i);
                tooltip.add(" - " + layer++ + ": #" + WearableColourUtils.getClosest(colour) + Integer.toHexString(colour));
            }
        }
    }

    public static WearableComponent getComponent(ItemStack stack) {
        return ComponentTagCompound.get(stack);
    }
}
