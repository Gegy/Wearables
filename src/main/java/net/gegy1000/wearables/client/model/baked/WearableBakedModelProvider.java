package net.gegy1000.wearables.client.model.baked;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.wearable.Wearable;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SideOnly(Side.CLIENT)
public class WearableBakedModelProvider implements IBakedModel {
    private final ItemOverrideList overrides = new Overrides();

    private final ImmutableMap<ResourceLocation, ImmutableList<BakedQuad>> quads;
    private final TextureAtlasSprite particle;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public WearableBakedModelProvider(ImmutableMap<ResourceLocation, ImmutableList<BakedQuad>> quads, TextureAtlasSprite particle, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
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
        private LoadingCache<Wearable.Data, IBakedModel> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .maximumSize(256)
                .build(new CacheLoader<Wearable.Data, IBakedModel>() {
                    @Override
                    public IBakedModel load(@Nonnull Wearable.Data data) {
                        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
                        for (ResourceLocation component : data.types) {
                            ImmutableList<BakedQuad> componentQuads = WearableBakedModelProvider.this.quads.get(component);
                            if (componentQuads != null) {
                                builder.addAll(componentQuads);
                            }
                        }
                        TextureAtlasSprite particle = WearableBakedModelProvider.this.particle;
                        ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = WearableBakedModelProvider.this.transforms;
                        return new WearableBakedModel(builder.build(), particle, transforms);
                    }
                });

        private Overrides() {
            super(Collections.emptyList());
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            if (stack.hasTagCompound()) {
                return this.cache.getUnchecked(WearableItem.getWearable(stack).toData());
            }
            return originalModel;
        }
    }
}
