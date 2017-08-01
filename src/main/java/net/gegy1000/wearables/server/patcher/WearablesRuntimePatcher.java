package net.gegy1000.wearables.server.patcher;

import net.gegy1000.wearables.server.core.WearablesClientHooks;
import net.ilexiconn.llibrary.server.asm.RuntimePatcher;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;

public class WearablesRuntimePatcher extends RuntimePatcher {
    @Override
    public void onInit() {
        this.patchClass(RenderPlayer.class)
                .patchMethod("applyRotations", AbstractClientPlayer.class, float.class, float.class, float.class, void.class)
                .apply(Patch.BEFORE, data -> data.node.getPrevious() != null && data.node.getPrevious().getOpcode() == INVOKESPECIAL, method -> method
                        .var(ALOAD, 0)
                        .var(ALOAD, 1)
                        .var(FLOAD, 4)
                        .method(INVOKESTATIC, WearablesClientHooks.class, "applyRotations", RenderPlayer.class, AbstractClientPlayer.class, float.class, void.class));
        /*this.patchClass(EntityLivingBase.class) TODO
                .patchMethod("isPotionActive", Potion.class, boolean.class)
                .apply(Patch.BEFORE, data -> data.node.getPrevious() == null, method -> method
                        .var(ALOAD, 0)
                        .var(ALOAD, 1)
                        .var(ALOAD, 0)
                        .field(GETFIELD, EntityLivingBase.class, "activePotionsMap", Map.class)
                        .method(INVOKESTATIC, WearablesHooks.class, "isPotionActive", EntityLivingBase.class, Potion.class, Map.class, boolean.class)
                        .node(IRETURN)).pop()
                .patchMethod("getActivePotionEffect", Potion.class, PotionEffect.class)
                .apply(Patch.BEFORE, data -> data.node.getPrevious() == null, method -> method
                        .var(ALOAD, 0)
                        .var(ALOAD, 1)
                        .var(ALOAD, 0)
                        .field(GETFIELD, EntityLivingBase.class, "activePotionsMap", Map.class)
                        .method(INVOKESTATIC, WearablesHooks.class, "getActivePotionEffect", EntityLivingBase.class, Potion.class, Map.class, PotionEffect.class)
                        .node(ARETURN));*/
    }
}
