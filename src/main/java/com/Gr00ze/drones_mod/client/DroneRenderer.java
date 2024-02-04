package com.Gr00ze.drones_mod.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.Gr00ze.drones_mod.DronesMod.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class DroneRenderer<M extends Mob> extends MobRenderer<M,DroneModel<M>> {

    ResourceLocation TEXTURE = new ResourceLocation(MOD_ID,"textures/entities/generic_drone.png");

    public DroneRenderer(EntityRendererProvider.Context erpC) {
        super(erpC, new DroneModel<>(erpC.bakeLayer(DroneModel.MODEL_LAYER)), 0);
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull M Mob) {
        return TEXTURE;
    }
}


