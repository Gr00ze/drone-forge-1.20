package com.Gr00ze.drones.client;

import com.Gr00ze.drones.entities.EntityDrone;
import com.Gr00ze.drones.entities.GenericDrone;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

public class EntityDroneRenderer extends EntityRenderer<EntityDrone> {
    public static GenericDroneModel model;
    ResourceLocation TEXTURE = new ResourceLocation(MOD_ID,"textures/entities/generic_drone.png");
    public EntityDroneRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        model = new GenericDroneModel<>(ctx.bakeLayer(GenericDroneModel.MODEL_LAYER));

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull EntityDrone entity) {

        return TEXTURE;
    }


    @Override
    public void render(EntityDrone droneEntity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(droneEntity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        VertexConsumer buffer = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        // Usa il PoseStack per applicare le trasformazioni necessarie al modello
        poseStack.pushPose();
        poseStack.translate(0.0, 1.5, 0.0); // Esempio di traslazione, adatta alla tua entità
        Quaternionf rotationQuaternion = new Quaternionf().rotationX((float) Math.PI);
        poseStack.rotateAround(rotationQuaternion, 0.0F, 0.0F, 0.0F);


        model.renderToBuffer(poseStack,buffer,packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        // Disegna il modello dell'entità
        // Sostituisci questo con il tuo codice di rendering effettivo
//        buffer.vertex(-0.5, 0.0, -0.5).color(255, 255, 255, 255).uv(0, 0).endVertex();
//        buffer.vertex(0.5, 0.0, -0.5).color(255, 255, 255, 255).uv(1, 0).endVertex();
//        buffer.vertex(0.5, 1.0, -0.5).color(255, 255, 255, 255).uv(1, 1).endVertex();
//        buffer.vertex(-0.5, 1.0, -0.5).color(255, 255, 255, 255).uv(0, 1).endVertex();

        // Ripristina la matrice di trasformazione
        poseStack.popPose();

    }


}
