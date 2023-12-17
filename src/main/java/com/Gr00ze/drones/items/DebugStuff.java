package com.Gr00ze.drones.items;

import com.Gr00ze.drones.entities.GenericDrone;
import com.Gr00ze.drones.gui.MyMenu;
import com.Gr00ze.drones.gui.MyScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = "drone_mod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DebugStuff extends Item {
    public Capability<Integer> ID_DRONE = CapabilityManager.get(new CapabilityToken<Integer>() {});
    private static boolean isSetNow = false;
    public GenericDrone genericDrone;

    private MyScreen screen;
    public DebugStuff() {

        super(new Item.Properties());
    }
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event ){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        ItemStack itemStack = player.getMainHandItem();
        boolean isDrone = entity instanceof GenericDrone;
        boolean isDebugStuff = itemStack.getItem() instanceof DebugStuff;
        if(!isDebugStuff)
            return;
        if(!player.isCrouching())
            return;
        if(!isDrone)
            System.out.println(("Is not a drone"));
        itemStack.getOrCreateTag().putInt("DRONE_ID",entity.getId());
        if(event.getLevel().isClientSide()){
            System.out.println(("EI: Client: id set "+entity.getId()));
        }else{
            System.out.println(("EI: Server: id set "+entity.getId()));
        }


    }
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event ){
        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();
        boolean isDebugStuff = itemStack.getItem() instanceof DebugStuff;
        if(!isDebugStuff)
            return;
        if(player.isCrouching())
            return;
        int entity_id = itemStack.getOrCreateTag().getInt("DRONE_ID");
        if(event.getLevel().isClientSide()){
            System.out.println(("RE: Client: id set "+entity_id));
            ClientLevel level = Minecraft.getInstance().level;
            if (level == null){
                System.out.println(("RE: Client: level nullo"));
                return;
            }
            Entity entity = level.getEntity(entity_id);
            MyScreen screen = new MyScreen(entity_id, (GenericDrone) entity);
            Minecraft.getInstance().setScreen(screen);
        }else{
            System.out.println(("RE: Server: id set "+entity_id));
        }
    }


    public static void registerEvents() {
        MinecraftForge.EVENT_BUS.register(DebugStuff.class);
    }
}
