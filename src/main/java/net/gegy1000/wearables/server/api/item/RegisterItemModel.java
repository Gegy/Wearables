package net.gegy1000.wearables.server.api.item;

public interface RegisterItemModel {
    default String getResource(String unlocalizedName) {
        return unlocalizedName;
    }
}
