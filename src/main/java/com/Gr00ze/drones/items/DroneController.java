package com.Gr00ze.drones.items;

import com.Gr00ze.drones.entities.GenericDrone;
import com.Gr00ze.drones.gui.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;


public class DroneController extends Item {

    public GenericDrone genericDrone;

    public DroneController() {
        super(new Item.Properties());
    }

    public void onEntityInteract(PlayerInteractEvent.EntityInteract event ){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        ItemStack itemStack = player.getMainHandItem();
        boolean isDrone = entity instanceof GenericDrone;
        boolean isDebugStuff = itemStack.getItem() instanceof DroneController;
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

    public void onRightClick(ItemStack itemStack){


    }


    public static void registerEvents() {
        MinecraftForge.EVENT_BUS.register(DroneController.class);
    }
}
