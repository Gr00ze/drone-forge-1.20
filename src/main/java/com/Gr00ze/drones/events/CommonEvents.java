package com.Gr00ze.drones.events;

import com.Gr00ze.drones.entities.GenericDrone;
import com.Gr00ze.drones.entities.Init;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {
    @SubscribeEvent
    public static void registerMobAttributes(EntityAttributeCreationEvent event){
        event.put(Init.GENERIC_DRONE.get(),GenericDrone.getMobAttributes());
    }
}
