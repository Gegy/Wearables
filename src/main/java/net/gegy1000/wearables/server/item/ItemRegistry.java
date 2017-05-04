package net.gegy1000.wearables.server.item;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.api.item.RegisterBlockEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableChestItemEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableFeetItemEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableHeadItemEntity;
import net.gegy1000.wearables.server.block.entity.wearable.WearableLegsItemEntity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ItemRegistry {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final DisplayMannequinItem DISPLAY_MANNEQUIN = new DisplayMannequinItem();
    public static final JetpackFuelItem JETPACK_FUEL = new JetpackFuelItem();

    public static final WearableItem WEARABLE_HEAD = new WearableItem(WearableHeadItemEntity.class, EntityEquipmentSlot.HEAD);
    public static final WearableItem WEARABLE_CHEST = new WearableItem(WearableChestItemEntity.class, EntityEquipmentSlot.CHEST);
    public static final WearableItem WEARABLE_LEGS = new WearableItem(WearableLegsItemEntity.class, EntityEquipmentSlot.LEGS);
    public static final WearableItem WEARABLE_FEET = new WearableItem(WearableFeetItemEntity.class, EntityEquipmentSlot.FEET);
    public static final WearableComponentItem WEARABLE_COMPONENT = new WearableComponentItem();

    public static void register() {
        try {
            for (Field field : ItemRegistry.class.getDeclaredFields()) {
                Object value = field.get(null);
                if (value instanceof Item) {
                    ItemRegistry.register((Item) value);
                } else if (value instanceof Item[]) {
                    for (Item item : (Item[]) value) {
                        ItemRegistry.register(item);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Item register(Item item) {
        ITEMS.add(item);
        String name = item.getUnlocalizedName().substring("item.".length());
        GameRegistry.register(item, new ResourceLocation(Wearables.MODID, name));
        if (item instanceof RegisterBlockEntity) {
            GameRegistry.registerTileEntity(((RegisterBlockEntity) item).getEntity(), Wearables.MODID + ":" + name);
        }
        return item;
    }
}
