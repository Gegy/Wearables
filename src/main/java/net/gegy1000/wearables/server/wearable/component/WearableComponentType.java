package net.gegy1000.wearables.server.wearable.component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mcp.MethodsReturnNonnullByDefault;
import net.gegy1000.wearables.server.movement.MovementHandler;
import net.gegy1000.wearables.server.movement.MovementHandlerRegistry;
import net.gegy1000.wearables.server.wearable.WearableCategory;
import net.gegy1000.wearables.server.wearable.event.ComponentEventHandler;
import net.gegy1000.wearables.server.wearable.event.ComponentEventRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

@MethodsReturnNonnullByDefault
public interface WearableComponentType extends IForgeRegistryEntry<WearableComponentType> {
    static WearableComponentType deserialize(JsonObject root) {
        ResourceLocation identifier = new ResourceLocation(JsonUtils.getString(root, "identifier"));

        String categoryIdentifier = JsonUtils.getString(root, "category");
        WearableCategory category = WearableCategory.get(categoryIdentifier);
        if (category == null) {
            throw new JsonSyntaxException("Unknown category type: \"" + categoryIdentifier + "\"");
        }

        JsonArray jsonIngredients = JsonUtils.getJsonArray(root, "ingredients");
        ItemStack[] ingredients = new ItemStack[jsonIngredients.size()];
        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i] = ShapedRecipes.deserializeItem(jsonIngredients.get(i).getAsJsonObject(), true);
        }

        JsonArray jsonLayers = JsonUtils.getJsonArray(root, "layers", new JsonArray());
        Layer[] layers = new Layer[jsonLayers.size()];
        Layer[] thinLayers = new Layer[jsonLayers.size()];
        for (int i = 0; i < layers.length; i++) {
            JsonObject jsonLayer = jsonLayers.get(i).getAsJsonObject();
            Layer layer = Layer.deserialize(jsonLayer);
            layers[i] = layer;
            if (jsonLayer.has("thin")) {
                thinLayers[i] = Layer.deserialize(JsonUtils.getJsonObject(jsonLayer, "thin"));
            } else {
                thinLayers[i] = layer;
            }
        }

        JsonArray jsonProperties = JsonUtils.getJsonArray(root, "properties", new JsonArray());
        Property[] properties = new Property[jsonProperties.size()];
        for (int i = 0; i < properties.length; i++) {
            properties[i] = Property.deserialize(jsonProperties.get(i).getAsJsonObject());
        }

        JsonArray jsonBounds = JsonUtils.getJsonArray(root, "bounds");
        if (jsonBounds.size() != 6) {
            throw new JsonSyntaxException("Expected 6 bounding box coordinates but got " + jsonBounds.size());
        }

        float[] boundCoordinates = new float[jsonBounds.size()];
        for (int i = 0; i < boundCoordinates.length; i++) {
            boundCoordinates[i] = jsonBounds.get(i).getAsFloat();
        }

        AxisAlignedBB bounds = new AxisAlignedBB(boundCoordinates[0], boundCoordinates[1], boundCoordinates[2], boundCoordinates[3], boundCoordinates[4], boundCoordinates[5]);

        boolean hasTooltip = JsonUtils.getBoolean(root, "has_tooltip", false);

        ComponentEventHandler eventHandler = null;
        MovementHandler movementHandler = null;

        if (root.has("event_handler")) {
            ResourceLocation eventHandlerIdentifier = new ResourceLocation(JsonUtils.getString(root, "event_handler"));
            eventHandler = ComponentEventRegistry.getRegistry().getValue(eventHandlerIdentifier);
        }

        if (root.has("movement_handler")) {
            ResourceLocation movementHandlerIdentifier = new ResourceLocation(JsonUtils.getString(root, "movement_handler"));
            movementHandler = MovementHandlerRegistry.getRegistry().getValue(movementHandlerIdentifier);
        }

        float[] inventoryRotation = null;

        if (root.has("inventory")) {
            JsonObject inventoryRoot = JsonUtils.getJsonObject(root, "inventory");
            JsonArray jsonRotation = JsonUtils.getJsonArray(inventoryRoot, "rotation", new JsonArray());

            if (jsonRotation.size() == 3) {
                inventoryRotation = new float[3];
                for (int i = 0; i < 3; i++) {
                    inventoryRotation[i] = jsonRotation.get(i).getAsFloat();
                }
            } else {
                throw new JsonSyntaxException("Received unexpected amount of rotation parameters, expected 3, got " + jsonRotation.size());
            }
        }

        return new WearableJsonComponent(identifier, category, ingredients, layers, thinLayers, inventoryRotation, properties, bounds, hasTooltip, eventHandler, movementHandler);
    }

    WearableCategory getCategory();

    ItemStack[] getIngredients();

    Layer[] getLayers(boolean thin);

    @Nullable
    float[] getInventoryRotation();

    Property[] getProperties();

    AxisAlignedBB getBounds();

    @Nullable
    ComponentEventHandler getEventHandler();

    @Nullable
    MovementHandler getMovementHandler();

    boolean hasTooltip();

    @Nullable
    Property getProperty(ComponentProperty type);

    @MethodsReturnNonnullByDefault
    class Layer {
        private final ResourceLocation model;
        private final ResourceLocation texture;
        private final ResourceLocation qualifiedTexture;
        private final boolean canColour;

        private Layer(ResourceLocation model, ResourceLocation texture, boolean canColour) {
            this.model = model;
            this.texture = texture;
            this.qualifiedTexture = texture != null ? new ResourceLocation(texture.getResourceDomain(), "textures/" + texture.getResourcePath() + ".png") : null;
            this.canColour = canColour;
        }

        public static Layer deserialize(JsonObject root) {
            ResourceLocation model = new ResourceLocation(JsonUtils.getString(root, "model"));
            ResourceLocation texture = null;
            if (root.has("texture")) {
                texture = new ResourceLocation(JsonUtils.getString(root, "texture"));
            }
            boolean canColour = JsonUtils.getBoolean(root, "can_colour", true);
            return new Layer(model, texture, canColour);
        }

        public ResourceLocation getModel() {
            return this.model;
        }

        @Nullable
        public ResourceLocation getTexture() {
            return this.texture;
        }

        @Nullable
        public ResourceLocation getQualifiedTexture() {
            return this.qualifiedTexture;
        }

        public boolean canColour() {
            return this.canColour;
        }
    }

    @MethodsReturnNonnullByDefault
    class Property {
        private final ResourceLocation identifier;
        private final float minimum;
        private final float maximum;

        private Property(ResourceLocation identifier, float minimum, float maximum) {
            this.identifier = identifier;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        public static Property deserialize(JsonObject root) {
            ResourceLocation identifier = new ResourceLocation(JsonUtils.getString(root, "identifier"));
            float minimum = JsonUtils.getFloat(root, "minimum");
            float maximum = JsonUtils.getFloat(root, "maximum");
            return new Property(identifier, minimum, maximum);
        }

        public ResourceLocation getIdentifier() {
            return this.identifier;
        }

        public float getMinimum() {
            return this.minimum;
        }

        public float getMaximum() {
            return this.maximum;
        }
    }
}
