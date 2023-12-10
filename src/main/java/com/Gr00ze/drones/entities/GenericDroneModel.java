package com.Gr00ze.drones.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.EntityModel;

import net.minecraft.client.model.FrogModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import org.jetbrains.annotations.NotNull;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

public class GenericDroneModel extends HierarchicalModel<GenericDrone> {

    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(MOD_ID,"generic_drone_model"),"main");
    private final ModelPart frame;
    private final ModelPart root;
    private final ModelPart elettronica;
    private final ModelPart sedia;

    public static AnimationState motorRotations;
    public GenericDroneModel(ModelPart root) {
        this.root = root;
        this.frame = root.getChild("frame");
        this.elettronica = root.getChild("elettronica");
        this.sedia = root.getChild("sedia");

        motorRotations = new AnimationState();


    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition frame = partdefinition.addOrReplaceChild("frame", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -2.0F, -17.0F, 30.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-15.0F, -2.0F, 15.0F, 30.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(-28, -28).addBox(-1.0F, -2.0F, -15.0F, 2.0F, 2.0F, 30.0F, new CubeDeformation(0.0F))
                .texOffs(-10, -10).addBox(-5.0F, -2.0F, -5.0F, 4.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(-10, -10).addBox(1.0F, -2.0F, -5.0F, 4.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition motor1 = frame.addOrReplaceChild("motor1", CubeListBuilder.create().texOffs(-2, -2).addBox(-16.0F, -7.0F, -18.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(1, 1).addBox(-14.5F, -10.0F, -16.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition elica_r1 = motor1.addOrReplaceChild("elica_r1", CubeListBuilder.create().texOffs(-5, -5).addBox(-0.5F, -1.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -9.0F, -12.1F, 0.0F, 0.0F, -0.7854F));

        PartDefinition elica_r2 = motor1.addOrReplaceChild("elica_r2", CubeListBuilder.create().texOffs(-5, -5).addBox(-0.5F, -1.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -9.0F, -20.1F, 0.0F, 0.0F, 0.7854F));

        PartDefinition motor2 = frame.addOrReplaceChild("motor2", CubeListBuilder.create().texOffs(-2, -2).addBox(-2.0F, 0.75F, -1.95F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(1, 1).addBox(-0.5F, -2.25F, -0.45F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.0F, -7.75F, 15.95F));

        PartDefinition elica_r3 = motor2.addOrReplaceChild("elica_r3", CubeListBuilder.create().texOffs(-5, -5).addBox(-0.5F, -1.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, 3.95F, 0.0F, 0.0F, -0.7854F));

        PartDefinition elica_r4 = motor2.addOrReplaceChild("elica_r4", CubeListBuilder.create().texOffs(-5, -5).addBox(-0.5F, -1.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, -4.05F, 0.0F, 0.0F, 0.7854F));

        PartDefinition motor3 = frame.addOrReplaceChild("motor3", CubeListBuilder.create().texOffs(-2, -2).addBox(-16.0F, -7.0F, -18.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(1, 1).addBox(-14.5F, -10.0F, -16.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(28.0F, 0.0F, 0.0F));

        PartDefinition elica_r5 = motor3.addOrReplaceChild("elica_r5", CubeListBuilder.create().texOffs(-5, -5).addBox(-0.5F, -1.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -9.0F, -12.1F, 0.0F, 0.0F, -0.7854F));

        PartDefinition elica_r6 = motor3.addOrReplaceChild("elica_r6", CubeListBuilder.create().texOffs(-5, -5).addBox(-0.5F, -1.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -9.0F, -20.1F, 0.0F, 0.0F, 0.7854F));

        PartDefinition motor4 = frame.addOrReplaceChild("motor4", CubeListBuilder.create().texOffs(-2, -2).addBox(-2.0F, 0.75F, -1.95F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(14.0F, -7.75F, 15.95F));

        PartDefinition rotore = motor4.addOrReplaceChild("rotore", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, -2.25F, -0.45F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition elica_r7 = rotore.addOrReplaceChild("elica_r7", CubeListBuilder.create().texOffs(-5, -5).addBox(-0.5F, -1.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, -4.05F, 0.0F, 0.0F, 0.7854F));

        PartDefinition elica_r8 = rotore.addOrReplaceChild("elica_r8", CubeListBuilder.create().texOffs(-5, -5).addBox(-0.5F, -1.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.25F, 3.95F, 0.0F, 0.0F, -0.7854F));

        PartDefinition elettronica = partdefinition.addOrReplaceChild("elettronica", CubeListBuilder.create().texOffs(-10, -10).addBox(-5.0F, 0.0F, -5.0F, 10.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition sedia = partdefinition.addOrReplaceChild("sedia", CubeListBuilder.create().texOffs(-10, -10).addBox(-5.0F, -4.0F, -5.0F, 8.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(-10, -10).addBox(3.0F, -16.0F, -5.0F, 2.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }
    @Override
    public void setupAnim(@NotNull GenericDrone genericDrone,  float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(motorRotations,GenericDroneAnimation.animation,ageInTicks * genericDrone.getW1());

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        frame.render(poseStack,vertexConsumer,light,overlay,red,green,blue,alpha);
        elettronica.render(poseStack,vertexConsumer,light,overlay,red,green,blue,alpha);
        sedia.render(poseStack,vertexConsumer,light,overlay,red,green,blue,alpha);

    }

    @Override
    public @NotNull ModelPart root() {
        return root;
    }
}
