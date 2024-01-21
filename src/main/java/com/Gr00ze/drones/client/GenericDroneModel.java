package com.Gr00ze.drones.client;

import com.Gr00ze.drones.entities.GenericDrone;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.Gr00ze.drones.DronesMod.MOD_ID;
@OnlyIn(Dist.CLIENT)
public class GenericDroneModel<M extends Mob> extends HierarchicalModel<M> {

    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(MOD_ID,"generic_drone_model"),"main");
    private final ModelPart frame;
    private final ModelPart root;
    private final ModelPart controls;
    private final ModelPart chair;
    private ModelPart motor1,motor2,motor3,motor4;

    public static AnimationState spinRotor1,spinRotor2,spinRotor3,spinRotor4, yawState;
    public GenericDroneModel(ModelPart root) {
        this.root = root;
        this.frame = root.getChild("frame");
        this.controls = frame.getChild("controls");
        this.chair = frame.getChild("chair");

        spinRotor1 = new AnimationState();
        spinRotor2 = new AnimationState();
        spinRotor3 = new AnimationState();
        spinRotor4 = new AnimationState();


    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition frame = partdefinition.addOrReplaceChild("frame", CubeListBuilder.create().texOffs(52, 0).addBox(-22.0F, -2.0F, -1.0F, 44.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 19).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 50).addBox(22.0F, -2.0F, -24.0F, 2.0F, 2.0F, 48.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-24.0F, -2.0F, -24.0F, 2.0F, 2.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.0F));

        PartDefinition foots = frame.addOrReplaceChild("foots", CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, 4.0F, -9.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(45.0F, 0.0F, -9.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 32).addBox(45.0F, 0.0F, 7.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(45.0F, 4.0F, -9.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(36, 20).addBox(-1.0F, 0.0F, 7.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 32).addBox(-1.0F, 0.0F, -9.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-23.0F, 0.0F, 0.0F));

        PartDefinition chair = frame.addOrReplaceChild("chair", CubeListBuilder.create().texOffs(52, 4).addBox(-7.0F, -3.0F, 3.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -9.0F));

        PartDefinition cube_r1 = chair.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 50).addBox(-7.0F, -7.0F, 2.5F, 14.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.4278F, 16.7206F, -0.3054F, 0.0F, 0.0F));

        PartDefinition controls = frame.addOrReplaceChild("controls", CubeListBuilder.create().texOffs(36, 7).addBox(7.0F, -7.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(34, 30).addBox(-9.0F, -7.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_control = controls.addOrReplaceChild("left_control", CubeListBuilder.create().texOffs(0, 20).addBox(7.4F, -10.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_control = controls.addOrReplaceChild("right_control", CubeListBuilder.create().texOffs(12, 20).addBox(-8.6F, -10.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition motors = frame.addOrReplaceChild("motors", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition motor1 = motors.addOrReplaceChild("motor1", CubeListBuilder.create().texOffs(22, 0).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-23.0F, -4.5F, -23.0F));

        PartDefinition rotor1 = motor1.addOrReplaceChild("rotor1", CubeListBuilder.create().texOffs(12, 9).addBox(-0.4333F, -1.1667F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0667F, -4.3333F, 0.0F));

        PartDefinition cube_r2 = rotor1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 29).addBox(-3.0F, -1.0F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.4667F, -0.1667F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r3 = rotor1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(22, 29).addBox(-3.0F, -1.0F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5333F, -0.1667F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition motor2 = motors.addOrReplaceChild("motor2", CubeListBuilder.create().texOffs(0, 20).addBox(-25.0F, -7.0F, -25.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(46.1F, 0.0F, 0.0F));

        PartDefinition rotor2 = motor2.addOrReplaceChild("rotor2", CubeListBuilder.create().texOffs(12, 0).addBox(-0.4333F, -1.1667F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-23.0667F, -8.8333F, -23.0F));

        PartDefinition cube_r4 = rotor2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(22, 23).addBox(-3.0F, -1.0F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.4667F, -0.1667F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r5 = rotor2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(22, 26).addBox(-3.0F, -1.0F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5333F, -0.1667F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition motor3 = motors.addOrReplaceChild("motor3", CubeListBuilder.create().texOffs(0, 9).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(23.1F, -4.5F, 23.0F));

        PartDefinition rotor3 = motor3.addOrReplaceChild("rotor3", CubeListBuilder.create().texOffs(0, 9).addBox(-0.4333F, -1.1667F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0667F, -4.3333F, 0.0F));

        PartDefinition cube_r6 = rotor3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(22, 15).addBox(-3.0F, -1.0F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.4667F, -0.1667F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r7 = rotor3.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(22, 20).addBox(-3.0F, -1.0F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5333F, -0.1667F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition motor4 = motors.addOrReplaceChild("motor4", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-22.9F, -4.5F, 23.0F));

        PartDefinition rotor4 = motor4.addOrReplaceChild("rotor4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.4333F, -1.1667F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0667F, -4.3333F, 0.0F));

        PartDefinition cube_r8 = rotor4.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(22, 9).addBox(-3.0F, -1.0F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.4667F, -0.1667F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r9 = rotor4.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(22, 12).addBox(-3.0F, -1.0F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5333F, -0.1667F, 0.0F, -0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public void setupAnim(@NotNull M mob,  float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if (mob instanceof GenericDrone genericDrone) {
            this.animate(spinRotor1, GenericDroneAnimation.spinRotor1, ageInTicks * genericDrone.getW1());
            this.animate(spinRotor2, GenericDroneAnimation.spinRotor2, ageInTicks * genericDrone.getW2());
            this.animate(spinRotor3, GenericDroneAnimation.spinRotor3, ageInTicks * genericDrone.getW3());
            this.animate(spinRotor4, GenericDroneAnimation.spinRotor4, ageInTicks * genericDrone.getW4());
            //System.out.println(genericDrone.getYawAngle());
            frame.yRot=genericDrone.getYawAngle();
            frame.zRot=genericDrone.getRollAngle();
            frame.xRot=-genericDrone.getPitchAngle();

        }
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        frame.render(poseStack,vertexConsumer,light,overlay,red,green,blue,alpha);


    }

    @Override
    public @NotNull ModelPart root() {
        return root;
    }



}
