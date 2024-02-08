package com.Gr00ze.drones_mod.items;

import com.Gr00ze.drones_mod.entities.AbstractDrone;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;


public class DroneController extends Item {
    public DroneController() {
        super(new Item.Properties());
    }

    public void onEntityInteract(PlayerInteractEvent.EntityInteract event){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        ItemStack itemStack = player.getMainHandItem();
        boolean isDrone = entity instanceof AbstractDrone;
        boolean isController = itemStack.getItem() instanceof DroneController;
        if(!isController)
            return;
        if(!player.isCrouching())
            return;
        if(!isDrone)
            System.out.println(("Is not a drone"));
        itemStack.getOrCreateTag().putInt("DRONE_ID",entity.getId());
        if(event.getLevel().isClientSide()){
            System.out.println(("EI: Client: id set "+entity.getId()));
        }else{
            System.out.println(("EI: Server: id set " + entity.getId()));
            if (entity instanceof AbstractDrone drone){
                System.out.printf("EI: Server: entity parameters roll kp %.2e\n", drone.getAllPIDControllers()[1].Kp);
            }
        }
    }

    public void onRightClick(ItemStack itemStack){

    }


    public static void registerEvents() {
        MinecraftForge.EVENT_BUS.register(DroneController.class);
    }
}
