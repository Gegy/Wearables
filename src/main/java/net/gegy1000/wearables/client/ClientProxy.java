package net.gegy1000.wearables.client;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.client.gui.DisplayMannequinGui;
import net.gegy1000.wearables.client.gui.WearableAssemblerGui;
import net.gegy1000.wearables.client.gui.WearableColouriserGui;
import net.gegy1000.wearables.client.gui.WearableFabricatorGui;
import net.gegy1000.wearables.client.model.BlankModel;
import net.gegy1000.wearables.client.model.baked.ComponentItemModel;
import net.gegy1000.wearables.client.model.baked.WearableItemModel;
import net.gegy1000.wearables.client.render.layer.WearableRenderLayer;
import net.gegy1000.wearables.server.ServerProxy;
import net.gegy1000.wearables.server.block.entity.DisplayMannequinEntity;
import net.gegy1000.wearables.server.block.entity.machine.WearableAssemblerEntity;
import net.gegy1000.wearables.server.block.entity.machine.WearableColouriserEntity;
import net.gegy1000.wearables.server.block.entity.machine.WearableFabricatorEntity;
import net.gegy1000.wearables.server.container.WearableAssemblerContainer;
import net.gegy1000.wearables.server.container.WearableColouriserContainer;
import net.gegy1000.wearables.server.item.ItemRegistry;
import net.gegy1000.wearables.server.item.WearableComponentItem;
import net.gegy1000.wearables.server.item.WearableItem;
import net.gegy1000.wearables.server.wearable.Wearable;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.gegy1000.wearables.server.wearable.component.WearableComponent;
import net.gegy1000.wearables.server.wearable.component.WearableComponentType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class ClientProxy extends ServerProxy {
    public static final BlankModel BLANK_MODEL = new BlankModel();
    private static final Minecraft MC = Minecraft.getMinecraft();

    public static Field quadList;

    @Override
    public void onPreInit() {
        super.onPreInit();

        ModelLoaderRegistry.registerLoader(ComponentItemModel.Loader.INSTANCE);
        ModelLoaderRegistry.registerLoader(WearableItemModel.Loader.INSTANCE);

        try {
            quadList = ReflectionHelper.findField(ModelBox.class, "field_78254_i", "quadList");
        } catch (ReflectionHelper.UnableToFindFieldException e) {
            Wearables.LOGGER.error("Failed to find quadList field", e);
        }
    }

    @Override
    public void onInit() {
        super.onInit();
    }

    @Override
    public void onPostInit() {
        super.onPostInit();
        Map<String, RenderPlayer> skinMap = MC.getRenderManager().getSkinMap();
        for (Map.Entry<String, RenderPlayer> entry : skinMap.entrySet()) {
            RenderPlayer renderer = entry.getValue();
            renderer.addLayer(new WearableRenderLayer(renderer));
        }
        for (Map.Entry<Class<? extends Entity>, Render<? extends Entity>> entry : MC.getRenderManager().entityRenderMap.entrySet()) {
            Render<? extends Entity> renderer = entry.getValue();
            if (renderer instanceof RenderLivingBase && !skinMap.containsValue(renderer)) {
                RenderLivingBase renderLiving = (RenderLivingBase) renderer;
                renderLiving.addLayer(new WearableRenderLayer(renderLiving));
            }
        }

        MC.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            WearableComponent component = WearableComponentItem.getComponent(stack);
            if (component != null) {
                return component.getColour(tintIndex);
            }
            return 0xFFFFFF;
        }, ItemRegistry.WEARABLE_COMPONENT);

        IItemColor wearableColourHandler = (stack, tintIndex) -> {
            Wearable wearable = WearableItem.getWearable(stack);
            if (wearable != null) {
                int id = tintIndex >> 16 & 0xFFFF;
                WearableComponentType type = ComponentRegistry.getRegistry().getValue(id);
                for (WearableComponent component : wearable.getComponents()) {
                    if (component.getType() == type) {
                        return component.getColour(tintIndex & 0xFFFF);
                    }
                }
            }
            return 0xFFFFFF;
        };

        MC.getItemColors().registerItemColorHandler(wearableColourHandler, ItemRegistry.WEARABLE_HEAD);
        MC.getItemColors().registerItemColorHandler(wearableColourHandler, ItemRegistry.WEARABLE_CHEST);
        MC.getItemColors().registerItemColorHandler(wearableColourHandler, ItemRegistry.WEARABLE_LEGS);
        MC.getItemColors().registerItemColorHandler(wearableColourHandler, ItemRegistry.WEARABLE_FEET);
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity entity = world.getTileEntity(pos);
        InventoryPlayer playerInventory = player.inventory;
        if (id == DISPLAY_MANNEQUIN_GUI && entity instanceof DisplayMannequinEntity) {
            return new DisplayMannequinGui(playerInventory, (DisplayMannequinEntity) entity);
        } else if (id == WEARABLE_FABRICATOR_GUI && entity instanceof WearableFabricatorEntity) {
            return new WearableFabricatorGui(playerInventory, (WearableFabricatorEntity) entity);
        } else if (id == WEARABLE_ASSEMBLER_GUI && entity instanceof WearableAssemblerEntity) {
            return new WearableAssemblerGui(playerInventory, pos, new WearableAssemblerContainer(playerInventory, (WearableAssemblerEntity) entity));
        } else if (id == WEARABLE_COLOURISER_GUI && entity instanceof WearableColouriserEntity) {
            return new WearableColouriserGui(player.inventory, pos, new WearableColouriserContainer(playerInventory, (WearableColouriserEntity) entity));
        }
        return null;
    }

    @Override
    public void schedule(Runnable runnable, MessageContext ctx) {
        if (ctx.side.isClient()) {
            MC.addScheduledTask(runnable);
        } else {
            super.schedule(runnable, ctx);
        }
    }

    @Override
    public EntityPlayer getPlayer(MessageContext ctx) {
        if (ctx.side.isClient()) {
            return MC.player;
        }
        return super.getPlayer(ctx);
    }

    @Override
    public boolean isClientPlayer(Entity entity) {
        return entity == MC.player;
    }

    public static TexturedQuad[] getQuadList(ModelBox box) {
        if (quadList != null) {
            try {
                return (TexturedQuad[]) quadList.get(box);
            } catch (IllegalAccessException e) {
                Wearables.LOGGER.error("Failed to access quadList", e);
            }
        }
        return new TexturedQuad[0];
    }
}
