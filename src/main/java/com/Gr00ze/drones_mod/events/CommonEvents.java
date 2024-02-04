package com.Gr00ze.drones_mod.events;

import com.Gr00ze.drones_mod.entities.AbstractDrone;
import com.Gr00ze.drones_mod.entities.GenericDrone;
import com.Gr00ze.drones_mod.entities.EntityInit;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Gr00ze.drones_mod.DronesMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {
    @SubscribeEvent
    public static void registerMobAttributes(EntityAttributeCreationEvent event){
        event.put(EntityInit.GENERIC_DRONE.get(),GenericDrone.getMobAttributes());
        event.put(EntityInit.DRONE.get(), AbstractDrone.getMobAttributes());
    }
}
