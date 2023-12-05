package com.Gr00ze.drones.events;

import com.Gr00ze.drones.entities.GenericDrone;
import com.Gr00ze.drones.entities.GenericDroneModel;
import com.Gr00ze.drones.entities.GenericDroneRendering;
import com.Gr00ze.drones.entities.Init;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {


    @SubscribeEvent
    public static void registerRenderers (EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(Init.GENERIC_DRONE.get(), GenericDroneRendering::new);
    }

    @SubscribeEvent
    public static void registerModels (EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(GenericDroneModel.MODEL_LAYER, GenericDroneModel::getLayerDefinitions);
    }

}
