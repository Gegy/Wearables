package net.gegy1000.wearables.server.container;

import net.minecraft.inventory.IContainerListener;

public abstract class SyncedContainer extends AutoTransferContainer {
    private final int[] fields;

    public SyncedContainer(int fieldCount) {
        this.fields = new int[fieldCount];
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : this.listeners) {
            for (int i = 0; i < this.fields.length; i++) {
                int value = this.getField(i);
                if (value != this.fields[i]) {
                    listener.sendWindowProperty(this, i, value);
                    this.fields[i] = value;
                }
            }
        }
    }

    @Override
    public void updateProgressBar(int id, int value) {
        super.updateProgressBar(id, value);
        this.setField(id, value);
    }

    public abstract void setField(int id, int value);

    public abstract int getField(int id);
}
