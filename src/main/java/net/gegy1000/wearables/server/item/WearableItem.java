package net.gegy1000.wearables.server.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.gegy1000.wearables.client.ClientProxy;
import net.gegy1000.wearables.server.api.item.RegisterBlockEntity;
import net.gegy1000.wearables.server.api.item.RegisterItemModel;
import net.gegy1000.wearables.server.block.DisplayMannequinBlock;
import net.gegy1000.wearables.server.tab.TabRegistry;
import net.gegy1000.wearables.server.util.WearableColourUtils;
import net.gegy1000.wearables.server.wearable.Wearable;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class WearableItem extends ItemArmor implements RegisterItemModel, RegisterBlockEntity, ISpecialArmor {
    public static final ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("wearable", "leather", -1, new int[4], 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

    private Class<? extends TileEntity> entity;

    public WearableItem(Class<? extends TileEntity> entity, EntityEquipmentSlot slot) {
        super(MATERIAL, 0, slot);
        this.entity = entity;
        this.setUnlocalizedName("wearable_" + slot.getName());
        this.setCreativeTab(TabRegistry.TEMPLATES);
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return this.entity;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
        for (WearableComponentType componentType : ComponentRegistry.COMPONENTS) {
            if (componentType.getCategory().getSlot() == this.armorType) {
                for (int colourIndex = 0; colourIndex < 16; colourIndex++) {
                    int colour = WearableColourUtils.fromRGBFloatArray(EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(colourIndex)));
                    Wearable wearable = new Wearable();
                    WearableComponent component = new WearableComponent(componentType);
                    wearable.addComponent(component);
                    for (int i = 0; i < componentType.getLayerCount(); i++) {
                        if (componentType.canColour(i)) {
                            component.setColour(i, colour);
                        }
                    }
                    ItemStack stack = new ItemStack(this);
                    stack.setTagCompound(wearable.serializeNBT());
                    subItems.add(stack);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        return ClientProxy.BLANK_MODEL;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        Wearable wearable = WearableItem.getWearable(stack);
        if (wearable.getAppliedArmour() != null) {
            tooltip.add(TextFormatting.BLUE + "" + TextFormatting.BOLD + "(" + wearable.getAppliedArmour().getDisplayName() + ")");
        }
        if (!wearable.getComponents().isEmpty()) {
            tooltip.add(I18n.translateToLocal("label.wearable_components.name"));
            for (WearableComponent component : wearable.getComponents()) {
                WearableComponentType type = component.getType();
                TextFormatting textColour = WearableColourUtils.getClosest(component.getColour(0));
                tooltip.add(" - " + textColour + I18n.translateToLocal("component." + type.getIdentifier() + ".name"));
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (!player.isSneaking()) {
            return super.onItemRightClick(stack, world, player, hand);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return world.getBlockState(pos).getBlock() instanceof DisplayMannequinBlock;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (!source.isUnblockable()) {
            ItemStack stack = WearableItem.getAppliedArmour(armor);
            if (stack != null && stack.getItem() instanceof ItemArmor) {
                ItemArmor item = (ItemArmor) stack.getItem();
                return new ArmorProperties(0, item.damageReduceAmount / 25.0, armor.getMaxDamage() + 1 - armor.getItemDamage());
            }
        }
        return new ArmorProperties(0, 0, -1);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        ItemStack appliedArmour = WearableItem.getAppliedArmour(armor);
        if (appliedArmour != null) {
            Item item = appliedArmour.getItem();
            if (item instanceof ItemArmor) {
                return ((ItemArmor) item).damageReduceAmount;
            }
        }
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        Wearable wearable = WearableItem.getWearable(stack);
        if (wearable.getAppliedArmour() != null) {
            wearable.getAppliedArmour().damageItem(damage, entity);
            if (wearable.getAppliedArmour().getItemDamage() > wearable.getAppliedArmour().getMaxDamage()) {
                wearable.setAppliedArmour(null);
            }
            stack.setTagCompound(wearable.serializeNBT());
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        ItemStack appliedArmour = WearableItem.getAppliedArmour(stack);
        if (appliedArmour != null) {
            return appliedArmour.getItem().getMaxDamage(appliedArmour);
        }
        return super.getMaxDamage(stack);
    }

    @Override
    public int getDamage(ItemStack stack) {
        ItemStack appliedArmour = WearableItem.getAppliedArmour(stack);
        if (appliedArmour != null) {
            return appliedArmour.getItem().getDamage(appliedArmour);
        }
        return super.getDamage(stack);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        ItemStack appliedArmour = WearableItem.getAppliedArmour(stack);
        if (appliedArmour != null) {
            return appliedArmour.getItem().showDurabilityBar(appliedArmour);
        }
        return super.showDurabilityBar(stack);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        ItemStack appliedArmour = WearableItem.getAppliedArmour(stack);
        if (appliedArmour != null) {
            return appliedArmour.getItem().getAttributeModifiers(slot, appliedArmour);
        }
        return HashMultimap.create();
    }

    public static Wearable getWearable(ItemStack stack) {
        NBTTagCompound compound = stack != null ? stack.getTagCompound() : null;
        if (compound == null) {
            compound = new NBTTagCompound();
        }
        return Wearable.deserialize(compound);
    }

    private static ItemStack getAppliedArmour(ItemStack stack) {
        Wearable wearable = getWearable(stack);
        if (wearable != null) {
            return wearable.getAppliedArmour();
        }
        return null;
    }

    public static WearableItem getItem(EntityEquipmentSlot slot) {
        switch (slot) {
            case HEAD:
                return ItemRegistry.WEARABLE_HEAD;
            case CHEST:
                return ItemRegistry.WEARABLE_CHEST;
            case LEGS:
                return ItemRegistry.WEARABLE_LEGS;
            case FEET:
                return ItemRegistry.WEARABLE_FEET;
        }
        return ItemRegistry.WEARABLE_CHEST;
    }

    public static WearableItem getItem(Wearable wearable) {
        if (wearable.getComponents().size() > 0) {
            return WearableItem.getItem(wearable.getComponents().get(0).getType().getCategory().getSlot());
        }
        return ItemRegistry.WEARABLE_CHEST;
    }
}
