package com.Gr00ze.drones.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event ){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        ItemStack itemStack = player.getMainHandItem();
        System.out.println("Tentativo cavalcaggio");
        boolean isDrone = entity instanceof Entity;
        if (isDrone && itemStack.isEmpty()){
            player.startRiding(entity);
        }
    }
    @SubscribeEvent
    public static void onJumpPlayer(LivingEvent.LivingJumpEvent event){
        System.out.println(event.getEntity().getName()+" sta saltando;");
    }
}
