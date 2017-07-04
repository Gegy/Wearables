package net.gegy1000.wearables.server.item;

import net.gegy1000.wearables.server.api.item.RegisterBlockEntity;
import net.gegy1000.wearables.server.api.item.RegisterItemModel;
import net.gegy1000.wearables.server.block.entity.wearable.WearableComponentEntity;
import net.gegy1000.wearables.server.tab.TabRegistry;
import net.gegy1000.wearables.server.util.WearableColourUtils;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import java.util.List;

public class WearableComponentItem extends Item implements RegisterItemModel, RegisterBlockEntity {
    public WearableComponentItem() {
        super();
        this.setUnlocalizedName("wearable_component");
        this.setCreativeTab(TabRegistry.GENERAL);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        WearableComponent component = WearableComponentItem.getComponent(stack);
        return I18n.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(stack) + ".name", I18n.translateToLocal("component." + component.getType().getIdentifier() + ".name"));
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return WearableComponentEntity.class;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (WearableComponentType componentType : ComponentRegistry.COMPONENTS) {
            WearableComponent component = new WearableComponent(componentType);
            ItemStack stack = new ItemStack(this);
            stack.setTagCompound(component.serializeNBT());
            subItems.add(stack);
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        WearableComponent component = WearableComponentItem.getComponent(stack);
        if (component.getType().hasTooltip()) {
            tooltip.add(TextFormatting.BOLD + I18n.translateToLocal("tooltip.wearable." + component.getType().getIdentifier() + ".name"));
        }
        tooltip.add(I18n.translateToLocal("label.wearable_layers.name"));
        int layer = 1;
        for (int i = 0; i < component.getType().getLayerCount(); i++) {
            if (component.getType().canColour(i)) {
                int colour = component.getColour(i);
                tooltip.add(" - " + layer++ + ": #" + WearableColourUtils.getClosest(colour) + Integer.toHexString(colour));
            }
        }
    }

    public static WearableComponent getComponent(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) {
            compound = new NBTTagCompound();
        }
        return WearableComponent.deserialize(compound);
    }
}
