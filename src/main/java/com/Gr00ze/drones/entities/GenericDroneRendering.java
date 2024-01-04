package com.Gr00ze.drones.entities;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class GenericDroneRendering extends MobRenderer<GenericDrone,GenericDroneModel> {

    ResourceLocation TEXTURE = new ResourceLocation(MOD_ID,"textures/entities/generic_drone.png");

    public GenericDroneRendering(EntityRendererProvider.Context erpC) {
        super(erpC, new GenericDroneModel(erpC.bakeLayer(GenericDroneModel.MODEL_LAYER)), 0);
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GenericDrone genericDrone) {
        return TEXTURE;
    }
}


