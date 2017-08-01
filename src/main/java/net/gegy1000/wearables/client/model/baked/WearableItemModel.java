package net.gegy1000.wearables.client.model.baked;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.render.ComponentRenderHandler;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.ilexiconn.llibrary.client.util.Matrix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Optional;
import java.util.function.Function;

public class WearableItemModel implements IModel {
    private final ImmutableList<ResourceLocation> textures;

    private final ItemCameraTransforms transforms;

    public WearableItemModel(ImmutableList<ResourceLocation> textures, ItemCameraTransforms transforms) {
        this.textures = textures;
        this.transforms = transforms;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> textures) {
        Matrix matrix = new Matrix();
        TRSRTransformation transformation = state.apply(Optional.empty()).orElse(TRSRTransformation.identity());
        matrix.multiply(transformation.getMatrix());

        ImmutableMap.Builder<ResourceLocation, ImmutableList<BakedQuad>> builder = ImmutableMap.builder();
        for (WearableComponentType componentType : ComponentRegistry.getRegistry()) {
            ResourceLocation identifier = componentType.getRegistryName();
            if (identifier != null) {
                ImmutableList.Builder<BakedQuad> componentBuilder = ImmutableList.builder();
                int id = ComponentRegistry.getRegistry().getID(identifier);
                ComponentRenderHandler.buildComponentQuads(componentType, matrix, componentBuilder, format, textures, (id & 0xFFFF) << 16);
                builder.put(identifier, componentBuilder.build());
            }
        }

        ResourceLocation particle = this.textures.isEmpty() ? new ResourceLocation("missingno") : this.textures.get(0);

        return new WearableBakedModelProvider(builder.build(), textures.apply(particle), PerspectiveMapWrapper.getTransforms(this.transforms));
    }

    @Override
    public ImmutableList<ResourceLocation> getTextures() {
        return this.textures;
    }

    public enum Loader implements ICustomModelLoader {
        INSTANCE;

        @Override
        public boolean accepts(ResourceLocation modelLocation) {
            if (modelLocation.getResourceDomain().equals(Wearables.MODID)) {
                for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
                    if (slot.getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
                        if (modelLocation.getResourcePath().equals("models/item/wearable_" + slot.getName())) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        @Override
        public IModel loadModel(ResourceLocation modelLocation) throws Exception {
            ResourceLocation path = new ResourceLocation(modelLocation.toString() + ".json");
            IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(path);
            ModelBlock modelBlock;
            try (Reader reader = new InputStreamReader(resource.getInputStream(), Charsets.UTF_8)) {
                modelBlock = ModelBlock.deserialize(reader);
                modelBlock.name = modelBlock.toString();
            }
            ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
            builder.add(new ResourceLocation(modelBlock.textures.getOrDefault("particle", "missingno")));
            for (WearableComponentType componentType : ComponentRegistry.getRegistry()) {
                WearableComponentType.Layer[] layers = componentType.getLayers(false);
                for (WearableComponentType.Layer layer : layers) {
                    ResourceLocation texture = layer.getTexture();
                    if (texture != null) {
                        builder.add(texture);
                    }
                }
            }
            return new WearableItemModel(builder.build(), modelBlock.getAllTransforms());
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {
        }
    }
}
