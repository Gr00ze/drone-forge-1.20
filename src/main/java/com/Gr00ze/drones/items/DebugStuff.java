package com.Gr00ze.drones.items;

import com.Gr00ze.drones.entities.GenericDrone;
import com.Gr00ze.drones.gui.MyMenu;
import com.Gr00ze.drones.gui.MyScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = "drone_mod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DebugStuff extends Item {

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

        if(entity instanceof GenericDrone){
            System.out.println(((GenericDrone) entity).getW1() +" " + player.level().isClientSide());
        }
        Item item = player.getMainHandItem().getItem();
        boolean isMainHand = event.getHand() == InteractionHand.MAIN_HAND;
        boolean isClientSide = player.level().isClientSide();
        boolean isDebugStuff = item instanceof DebugStuff;
        if (!isDebugStuff)return;
        if(entity instanceof GenericDrone){
            if(isMainHand)isSetNow = true;
            if( isMainHand && !isClientSide){

                ((DebugStuff) item).genericDrone = (GenericDrone) entity;
                player.sendSystemMessage(Component.literal("Drone set on "+entity));

            }
        }else{
            player.sendSystemMessage(Component.literal("Non è un drone"));
        }

    }
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event ){


        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();
        Item item = itemStack.getItem();
        boolean isMainHand = event.getHand() == InteractionHand.MAIN_HAND;
        boolean isClientSide = player.level().isClientSide();
        boolean isDebugStuff = item instanceof DebugStuff;
        if(isDebugStuff && !isSetNow) {
            if (((DebugStuff) item).genericDrone == null)return;


            if( isClientSide && isMainHand ){
                System.out.println("Evento chiamato C"+isSetNow);

                MyScreen screen = new MyScreen(((DebugStuff) item).genericDrone);
                player.sendSystemMessage(Component.literal("Open GUI on "+((DebugStuff) item).genericDrone));

                Minecraft.getInstance().setScreen(screen);
            }

            if (!isClientSide){
                System.out.println("Evento chiamato S"+isSetNow);


//                NetworkHooks.openScreen((ServerPlayer) player,
//                        new SimpleMenuProvider(
//                                (containerId, playerInventory, player2) -> new MyMenu(containerId, playerInventory),
//                                Component.translatable("menu.title.drone_mod.mymenu")),player.blockPosition());
            }
        }
        isSetNow = false;
    }


    public static void registerEvents() {
        MinecraftForge.EVENT_BUS.register(DebugStuff.class);
    }
}
