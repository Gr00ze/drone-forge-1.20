package com.Gr00ze.drones_mod.items;

import com.Gr00ze.drones_mod.entities.AbstractDrone;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import static com.Gr00ze.drones_mod.DronesMod.printDebug;


public class DroneController extends Item {
    public DroneController() {
        super(new Item.Properties());
    }

    public void onEntityInteract(PlayerInteractEvent.EntityInteract event){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        Level level = event.getLevel();
        boolean isClient = level.isClientSide();
        ItemStack itemStack = player.getMainHandItem();
        boolean isDrone = entity instanceof AbstractDrone;
        boolean isController = itemStack.getItem() instanceof DroneController;
        if(!isController)
            return;
        if(!player.isCrouching())
            return;
        if(!isDrone)
            printDebug(isClient,"Is not a drone");
        itemStack.getOrCreateTag().putInt("DRONE_ID",entity.getId());
        if(isClient){
            printDebug(true,"InteractEvent: id set %d",entity.getId());
        }else{
            printDebug(false,"InteractEvent: id set %d",entity.getId());
            if (entity instanceof AbstractDrone drone){
                printDebug(false,"InteractEvent: entity parameters roll kp %.2e\n", drone.getAllPIDControllers()[1].Kp);
            }
        }
    }

    public void onRightClick(ItemStack itemStack){

    }


    public static void registerEvents() {
        MinecraftForge.EVENT_BUS.register(DroneController.class);
    }
}
