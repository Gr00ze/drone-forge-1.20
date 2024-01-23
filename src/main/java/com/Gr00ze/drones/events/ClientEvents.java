package com.Gr00ze.drones.events;

import com.Gr00ze.drones.client.*;
import com.Gr00ze.drones.entities.EntityInit;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {


    @SubscribeEvent
    public static void registerRenderers (EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityInit.GENERIC_DRONE.get(), GenericDroneRendering::new);
        event.registerEntityRenderer(EntityInit.HORSE_DRONE.get(), HorseDroneRendering::new);
        event.registerEntityRenderer(EntityInit.BOAT_DRONE.get(), BoatDroneRendering::new);
        event.registerEntityRenderer(EntityInit.ENTITY_DRONE.get(), EntityDroneRenderer::new);
    }

    @SubscribeEvent
    public static void registerModels (EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(GenericDroneModel.MODEL_LAYER, GenericDroneModel::createBodyLayer);
        event.registerLayerDefinition(HorseDroneModel.MODEL_LAYER, HorseDroneModel::createBodyLayer);
        event.registerLayerDefinition(BoatDroneModel.MODEL_LAYER, BoatDroneModel::createBodyLayer);
    }



}
