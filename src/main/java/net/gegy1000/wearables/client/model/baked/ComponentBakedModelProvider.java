package net.gegy1000.wearables.client.model.baked;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.gegy1000.wearables.server.item.WearableComponentItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ComponentBakedModelProvider implements IBakedModel {
    private final ItemOverrideList overrides = new Overrides();

    private final ImmutableMap<ResourceLocation, ImmutableList<BakedQuad>> quads;
    private final TextureAtlasSprite particle;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public ComponentBakedModelProvider(ImmutableMap<ResourceLocation, ImmutableList<BakedQuad>> quads, TextureAtlasSprite particle, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        this.quads = quads;
        this.particle = particle;
        this.transforms = transforms;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return Collections.emptyList();
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return this.particle;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return this.overrides;
    }

    private final class Overrides extends ItemOverrideList {
        private LoadingCache<ResourceLocation, IBakedModel> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.MINUTES)
                .build(new CacheLoader<ResourceLocation, IBakedModel>() {
                    @Override
                    public IBakedModel load(@Nonnull ResourceLocation key) {
                        ImmutableList<BakedQuad> componentQuads = ComponentBakedModelProvider.this.quads.get(key);
                        TextureAtlasSprite particle = ComponentBakedModelProvider.this.particle;
                        ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = ComponentBakedModelProvider.this.transforms;
                        return new ComponentBakedModel(componentQuads, particle, transforms);
                    }
                });

        private Overrides() {
            super(Collections.emptyList());
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            if (stack.hasTagCompound()) {
                ResourceLocation identifier = WearableComponentItem.getComponent(stack).getType().getRegistryName();
                if (identifier != null) {
                    return this.cache.getUnchecked(identifier);
                }
            }
            return originalModel;
        }
    }
}
