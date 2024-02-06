package com.Gr00ze.drones_mod.events;

import com.Gr00ze.drones_mod.client.*;
import com.Gr00ze.drones_mod.entities.EntityInit;

import com.Gr00ze.drones_mod.entities.GenericDrone;
import com.mojang.blaze3d.platform.VideoMode;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Optional;

import static com.Gr00ze.drones_mod.DronesMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {


    @SubscribeEvent
    public static void registerRenderers (EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityInit.GENERIC_DRONE.get(), GenericDroneRendering::new);
        event.registerEntityRenderer(EntityInit.ENTITY_DRONE.get(), EntityDroneRenderer::new);
        event.registerEntityRenderer(EntityInit.DRONE.get(), DroneRenderer::new);
    }

    @SubscribeEvent
    public static void registerModels (EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(GenericDroneModel.MODEL_LAYER, GenericDroneModel::createBodyLayer);
        event.registerLayerDefinition(DroneModel.MODEL_LAYER, DroneModel::createBodyLayer);
    }


    @SubscribeEvent
    public static void onSetup(FMLClientSetupEvent event){
        System.out.println("Evento FMLClient chiamato");
        int h = 200, w = 300;
        Minecraft.getInstance().getWindow().setWindowed(w,h);

    }


}
