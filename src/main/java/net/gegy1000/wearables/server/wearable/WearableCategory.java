package net.gegy1000.wearables.server.wearable;

import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public enum WearableCategory {
    HEAD_FACE(EntityEquipmentSlot.HEAD, "face"),
    HEAD_TOP(EntityEquipmentSlot.HEAD, "top"),
    HEAD_HORNS(EntityEquipmentSlot.HEAD, "horns"),
    HEAD_GENERIC(EntityEquipmentSlot.HEAD, "generic"),
    CHEST_GENERIC(EntityEquipmentSlot.CHEST, "generic"),
    CHEST_ARMS(EntityEquipmentSlot.CHEST, "arms"),
    CHEST_NECK(EntityEquipmentSlot.CHEST, "neck"),
    CHEST_UTILITY(EntityEquipmentSlot.CHEST, "utility"),
    CHEST_BACK(EntityEquipmentSlot.CHEST, "back"),
    LEGS_GENERIC(EntityEquipmentSlot.LEGS, "generic"),
    FEET_GENERIC(EntityEquipmentSlot.FEET, "generic");

    private static final Map<String, WearableCategory> CATEGORIES = new HashMap<>();

    static {
        for (WearableCategory category : WearableCategory.values()) {
            CATEGORIES.put(category.getIdentifier(), category);
        }
    }

    private EntityEquipmentSlot slot;
    private String identifier;

    WearableCategory(EntityEquipmentSlot slot, String identifier) {
        this.slot = slot;
        this.identifier = slot.getName() + "_" + identifier;
    }

    public EntityEquipmentSlot getSlot() {
        return this.slot;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    @Nullable
    public static WearableCategory get(String identifier) {
        return CATEGORIES.get(identifier);
    }
}
