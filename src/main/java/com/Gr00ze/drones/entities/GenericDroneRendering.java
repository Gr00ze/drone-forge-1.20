package com.Gr00ze.drones.entities;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GenericDroneRendering extends MobRenderer<GenericDrone,GenericDroneModel> {

    ResourceLocation TEXTURE = new ResourceLocation("textures/entities/generic_drone.png");

    public GenericDroneRendering(EntityRendererProvider.Context erpC) {
        super(erpC, new GenericDroneModel(erpC.bakeLayer(GenericDroneModel.MODEL_LAYER)), 0);
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GenericDrone genericDrone) {
        return TEXTURE;
    }
}


