package net.gegy1000.wearables.server;

import net.gegy1000.wearables.Wearables;
import net.gegy1000.wearables.server.block.BlockRegistry;
import net.gegy1000.wearables.server.block.entity.DisplayMannequinEntity;
import net.gegy1000.wearables.server.block.entity.machine.WearableAssemblerEntity;
import net.gegy1000.wearables.server.block.entity.machine.WearableColouriserEntity;
import net.gegy1000.wearables.server.block.entity.machine.WearableFabricatorEntity;
import net.gegy1000.wearables.server.container.DisplayMannequinContainer;
import net.gegy1000.wearables.server.container.WearableAssemblerContainer;
import net.gegy1000.wearables.server.container.WearableColouriserContainer;
import net.gegy1000.wearables.server.container.WearableFabricatorContainer;
import net.gegy1000.wearables.server.item.ItemRegistry;
import net.gegy1000.wearables.server.network.SetColourMessage;
import net.gegy1000.wearables.server.network.SetPropertyMessage;
import net.gegy1000.wearables.server.network.SetSelectedComponentMessage;
import net.gegy1000.wearables.server.network.SyncConfigMessage;
import net.gegy1000.wearables.server.network.UpdateMovementMessage;
import net.gegy1000.wearables.server.recipe.RecipeRegistry;
import net.gegy1000.wearables.server.wearable.component.ComponentRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ServerProxy implements IGuiHandler {
    public static final int DISPLAY_MANNEQUIN_GUI = 0;
    public static final int WEARABLE_FABRICATOR_GUI = 1;
    public static final int WEARABLE_ASSEMBLER_GUI = 2;
    public static final int WEARABLE_COLOURISER_GUI = 3;

    public void onPreInit() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Wearables.INSTANCE, this);

        ComponentRegistry.register();
        BlockRegistry.register();
        ItemRegistry.register();

        RecipeRegistry.register();

        Wearables.NETWORK_WRAPPER.registerMessage(SetSelectedComponentMessage.Handler.class, SetSelectedComponentMessage.class, 0, Side.SERVER);
        Wearables.NETWORK_WRAPPER.registerMessage(SetColourMessage.Handler.class, SetColourMessage.class, 1, Side.SERVER);
        Wearables.NETWORK_WRAPPER.registerMessage(SetPropertyMessage.Handler.class, SetPropertyMessage.class, 2, Side.SERVER);
        Wearables.NETWORK_WRAPPER.registerMessage(UpdateMovementMessage.Handler.class, UpdateMovementMessage.class, 3, Side.SERVER);
        Wearables.NETWORK_WRAPPER.registerMessage(UpdateMovementMessage.Handler.class, UpdateMovementMessage.class, 4, Side.CLIENT);
        Wearables.NETWORK_WRAPPER.registerMessage(SyncConfigMessage.Handler.class, SyncConfigMessage.class, 5, Side.CLIENT);
    }

    public void onInit() {
    }

    public void onPostInit() {
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        IBlockState state = world.getBlockState(pos);
        TileEntity entity = world.getTileEntity(pos);
        if (id == DISPLAY_MANNEQUIN_GUI && entity instanceof DisplayMannequinEntity) {
            return new DisplayMannequinContainer(player.inventory, (DisplayMannequinEntity) entity);
        } else if (id == WEARABLE_FABRICATOR_GUI && entity instanceof WearableFabricatorEntity) {
            return new WearableFabricatorContainer(player.inventory, (WearableFabricatorEntity) entity);
        } else if (id == WEARABLE_ASSEMBLER_GUI && entity instanceof WearableAssemblerEntity) {
            return new WearableAssemblerContainer(player.inventory, (WearableAssemblerEntity) entity);
        } else if (id == WEARABLE_COLOURISER_GUI && entity instanceof WearableColouriserEntity) {
            return new WearableColouriserContainer(player.inventory, (WearableColouriserEntity) entity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public void schedule(Runnable runnable, MessageContext ctx) {
        WorldServer server = ctx.getServerHandler().player.getServerWorld();
        server.addScheduledTask(runnable);
    }

    public EntityPlayer getPlayer(MessageContext ctx) {
        return ctx.getServerHandler().player;
    }

    public boolean isClientPlayer(Entity entity) {
        return false;
    }
}
