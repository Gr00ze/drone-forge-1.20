package com.Gr00ze.drones.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

public class GenericDroneModel extends EntityModel<GenericDrone> {

    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(MOD_ID,"generic_drone_model"),"main");
    ModelPart body;
    public GenericDroneModel(ModelPart modelPart) {
        body = modelPart.getChild("body");
    }

    public static LayerDefinition getLayerDefinitions(){
        MeshDefinition meshDefinition = new MeshDefinition();
        meshDefinition.getRoot().addOrReplaceChild("body",new CubeListBuilder()
                .addBox(0,0,0,1,1,1),
                PartPose.ZERO);

        return LayerDefinition.create(meshDefinition,128,128);
    }
    @Override
    public void setupAnim(@NotNull GenericDrone genericDrone, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack ps, @NotNull VertexConsumer vc, int i1, int i2, float f1, float f2, float f3, float f4) {
        body.render(ps,vc,i1,i2,f1,f2,f3,f4);
    }
}
