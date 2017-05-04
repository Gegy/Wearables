package net.gegy1000.wearables.server.patcher;

import net.gegy1000.wearables.server.core.WearablesClientHooks;
import net.gegy1000.wearables.server.core.WearablesHooks;
import net.ilexiconn.llibrary.server.asm.RuntimePatcher;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.ForgeHooksClient;

import java.util.Map;

public class WearablesRuntimePatcher extends RuntimePatcher {
    @Override
    public void onInit() {
        this.patchClass(TileEntityItemStackRenderer.class)
                .patchMethod("renderByItem", ItemStack.class, void.class)
                .apply(Patch.BEFORE, data -> data.node.getPrevious() == null, method -> method
                        .var(ALOAD, 1)
                        .method(INVOKESTATIC, WearablesClientHooks.class, "updateRenderedStack", ItemStack.class, void.class));
        this.patchClass(ForgeHooksClient.class)
                .patchMethod("handleCameraTransforms", IBakedModel.class, ItemCameraTransforms.TransformType.class, boolean.class, IBakedModel.class)
                .apply(Patch.BEFORE, data -> data.node.getPrevious() == null, method -> method
                        .var(ALOAD, 1)
                        .method(INVOKESTATIC, WearablesClientHooks.class, "updateCameraTransform", ItemCameraTransforms.TransformType.class, void.class));
        this.patchClass(EnchantmentHelper.class)
                .patchMethod("getDepthStriderModifier", EntityLivingBase.class, int.class)
                .apply(Patch.BEFORE, data -> data.node.getOpcode() == IRETURN, method -> method
                        .var(ISTORE, 1)
                        .var(ALOAD, 0)
                        .var(ILOAD, 1)
                        .method(INVOKESTATIC, WearablesHooks.class, "getDepthStriderModifier", EntityLivingBase.class, int.class, int.class)
                        .var(ISTORE, 1)
                        .var(ILOAD, 1));
        this.patchClass(RenderPlayer.class)
                .patchMethod("applyRotations", AbstractClientPlayer.class, float.class, float.class, float.class, void.class)
                .apply(Patch.BEFORE, data -> data.node.getPrevious() != null && data.node.getPrevious().getOpcode() == INVOKESPECIAL, method -> method
                        .var(ALOAD, 0)
                        .var(ALOAD, 1)
                        .var(FLOAD, 2)
                        .var(FLOAD, 3)
                        .var(FLOAD, 4)
                        .method(INVOKESTATIC, WearablesClientHooks.class, "applyRotations", RenderPlayer.class, AbstractClientPlayer.class, float.class, float.class, float.class, void.class));
        this.patchClass(EntityLivingBase.class)
                .patchMethod("isPotionActive", Potion.class, boolean.class)
                .apply(Patch.BEFORE, data -> data.node.getPrevious() == null, method -> method
                        .var(ALOAD, 0)
                        .field(GETFIELD, EntityLivingBase.class, "activePotionsMap", Map.class)
                        .var(ASTORE, 2)
                        .var(ALOAD, 0)
                        .var(ALOAD, 1)
                        .var(ALOAD, 2)
                        .method(INVOKESTATIC, WearablesHooks.class, "isPotionActive", EntityLivingBase.class, Potion.class, Map.class, boolean.class)
                        .node(IRETURN)).pop()
                .patchMethod("getActivePotionEffect", Potion.class, PotionEffect.class)
                .apply(Patch.BEFORE, data -> data.node.getPrevious() == null, method -> method
                        .var(ALOAD, 0)
                        .field(GETFIELD, EntityLivingBase.class, "activePotionsMap", Map.class)
                        .var(ASTORE, 2)
                        .var(ALOAD, 0)
                        .var(ALOAD, 1)
                        .var(ALOAD, 2)
                        .method(INVOKESTATIC, WearablesHooks.class, "getActivePotionEffect", EntityLivingBase.class, Potion.class, Map.class, PotionEffect.class)
                        .node(ARETURN));
    }
}
