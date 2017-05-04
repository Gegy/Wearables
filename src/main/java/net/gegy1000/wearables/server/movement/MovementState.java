package net.gegy1000.wearables.server.movement;

import net.minecraft.entity.player.EntityPlayer;

public class MovementState {
    private final EntityPlayer player;

    private byte flags;

    private boolean dirty;

    public MovementState(EntityPlayer player) {
        this.player = player;
    }

    public void setMoveUp(boolean moveUp) {
        this.setFlag(0, moveUp);
    }

    public void setMoveForward(boolean moveForward) {
        this.setFlag(1, moveForward);
    }

    public void setMoveBackward(boolean moveBackward) {
        this.setFlag(2, moveBackward);
    }

    public void setHasFuel(boolean hasFuel) {
        this.setFlag(3, hasFuel);
    }

    public void setFlyToggle(boolean toggle) {
        this.setFlag(4, toggle);
    }

    public boolean shouldMoveUp() {
        return this.getFlag(0);
    }

    public boolean shouldMoveForward() {
        return this.getFlag(1);
    }

    public boolean shouldMoveBackward() {
        return this.getFlag(2);
    }

    public boolean hasFuel() {
        return this.getFlag(3);
    }

    public boolean isFlyToggle() {
        return this.getFlag(4);
    }

    public EntityPlayer getPlayer() {
        return this.player;
    }

    public void unmarkDirty() {
        this.dirty = false;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    private void setFlag(int i, boolean value) {
        if (this.getFlag(i) != value) {
            this.dirty = true;
        }
        if (value) {
            this.flags = (byte) (this.flags | (1 << i));
        } else {
            this.flags = (byte) (this.flags & ~(1 << i));
        }
    }

    private boolean getFlag(int i) {
        return (this.flags >> i & 1) != 0;
    }

    public void setFlags(byte flags) {
        if (flags != this.flags) {
            this.dirty = true;
        }
        this.flags = flags;
    }

    public byte getFlags() {
        return this.flags;
    }

    public void apply(LocalPlayerState localState) {
        localState.setFlyToggle(this.isFlyToggle());
    }
}
