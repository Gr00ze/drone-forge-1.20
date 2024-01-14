package com.Gr00ze.drones.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class BoatDroneRendering<E extends Entity> extends EntityRenderer<E> {
    public final BoatDroneModel<Boat> model;
    public final int shadow;
    ResourceLocation TEXTURE = new ResourceLocation(MOD_ID,"textures/entities/generic_drone.png");

    public BoatDroneRendering(EntityRendererProvider.Context erpC) {
        super(erpC);
        model = new BoatDroneModel<>(erpC.bakeLayer(GenericDroneModel.MODEL_LAYER));
        shadow = 0;
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull E entity) {
        return TEXTURE;
    }

    @Override
    public void render(@NotNull E entity, float yaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        matrixStack.pushPose();
        matrixStack.translate(0.0D, 1.5D, 0.0D);
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotateAxis((float) Math.toRadians(180), 1, 0, 0);
        matrixStack.mulPose(quaternionf);
        this.model.renderToBuffer(matrixStack, buffer.getBuffer(RenderType.entityCutout(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
        super.render(entity, yaw, partialTicks, matrixStack, buffer, packedLight);
    }
}