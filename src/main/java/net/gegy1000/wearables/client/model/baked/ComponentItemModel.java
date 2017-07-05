package net.gegy1000.wearables.client.model.baked;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.model.component.ComponentModelRegistry;
import net.gegy1000.wearables.client.model.component.WearableComponentModel;
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

public class ComponentItemModel implements IModel {
    private final ImmutableList<ResourceLocation> textures;

    private final ItemCameraTransforms transforms;

    public ComponentItemModel(ImmutableList<ResourceLocation> textures, ItemCameraTransforms transforms) {
        this.textures = textures;
        this.transforms = transforms;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> textures) {
        Matrix matrix = new Matrix();
        TRSRTransformation transformation = state.apply(Optional.empty()).orElse(TRSRTransformation.identity());
        matrix.multiply(transformation.getMatrix());

        matrix.scale(-1.0F, -1.0F, 1.0F);

        ImmutableMap.Builder<ResourceLocation, ImmutableList<BakedQuad>> builder = ImmutableMap.builder();
        for (WearableComponentType componentType : ComponentRegistry.getRegistry().getValues()) {
            ResourceLocation identifier = componentType.getRegistryName();
            if (identifier != null) {
                ImmutableList.Builder<BakedQuad> componentBuilder = ImmutableList.builder();
                this.buildComponentQuads(componentType, matrix, componentBuilder, format, textures);
                builder.put(identifier, componentBuilder.build());
            }
        }

        ResourceLocation particle = this.textures.isEmpty() ? new ResourceLocation("missingno") : this.textures.get(0);

        return new ComponentBakedModelProvider(builder.build(), textures.apply(particle), PerspectiveMapWrapper.getTransforms(this.transforms));
    }

    @Override
    public ImmutableList<ResourceLocation> getTextures() {
        return this.textures;
    }

    private void buildComponentQuads(WearableComponentType type, Matrix matrix, ImmutableList.Builder<BakedQuad> componentBuilder, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> textures) {
        matrix.push();

        ComponentRenderHandler.fitSlot(type.getBounds(), matrix);

        matrix.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        matrix.scale(0.0625F, 0.0625F, 0.0625F);

        WearableComponentType.Layer[] layers = type.getLayers(false);

        for (WearableComponentType.Layer layer : layers) {
            WearableComponentModel model = ComponentModelRegistry.getRegistry().getValue(layer.getModel());
            if (model == null) {
                throw new RuntimeException("Received null model for component " + type.getRegistryName());
            }

            TextureAtlasSprite sprite = textures.apply(layer.getTexture() != null ? layer.getTexture() : new ResourceLocation("missingno"));

            model.buildQuads(matrix, componentBuilder, format, sprite);
        }

        matrix.pop();
    }

    public enum Loader implements ICustomModelLoader {
        INSTANCE;

        @Override
        public boolean accepts(ResourceLocation modelLocation) {
            return modelLocation.getResourceDomain().equals(Wearables.MODID) && modelLocation.getResourcePath().equals("models/item/wearable_component");
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
            for (WearableComponentType componentType : ComponentRegistry.getRegistry().getValues()) {
                WearableComponentType.Layer[] layers = componentType.getLayers(false);
                for (WearableComponentType.Layer layer : layers) {
                    ResourceLocation texture = layer.getTexture();
                    if (texture != null) {
                        builder.add(texture);
                    }
                }
            }
            return new ComponentItemModel(builder.build(), modelBlock.getAllTransforms());
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {
        }
    }
}
