package net.gegy1000.wearables.server.wearable;

import net.minecraft.inventory.EntityEquipmentSlot;

public enum WearableCategory {
    HEAD_FACE(EntityEquipmentSlot.HEAD, "face"),
    HEAD_TOP(EntityEquipmentSlot.HEAD, "top"),
    HEAD_GENERIC(EntityEquipmentSlot.HEAD, "generic"),
    CHEST_GENERIC(EntityEquipmentSlot.CHEST, "generic"),
    CHEST_ARMS(EntityEquipmentSlot.CHEST, "arms"),
    CHEST_NECK(EntityEquipmentSlot.CHEST, "neck"),
    CHEST_UTILITY(EntityEquipmentSlot.CHEST, "utility"),
    CHEST_BACK(EntityEquipmentSlot.CHEST, "back"),
    LEGS_GENERIC(EntityEquipmentSlot.LEGS, "generic"),
    FEET_GENERIC(EntityEquipmentSlot.FEET, "generic");

    private EntityEquipmentSlot slot;
    private String identifier;

    WearableCategory(EntityEquipmentSlot slot, String identifier) {
        this.slot = slot;
        this.identifier = slot.getName() + "." + identifier;
    }

    public EntityEquipmentSlot getSlot() {
        return this.slot;
    }

    public String getIdentifier() {
        return this.identifier;
    }
}
