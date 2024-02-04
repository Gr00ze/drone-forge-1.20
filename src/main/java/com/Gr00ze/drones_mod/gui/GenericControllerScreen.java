package com.Gr00ze.drones_mod.gui;

import com.Gr00ze.drones_mod.entities.GenericDrone;
import com.Gr00ze.drones_mod.network.ControllerPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static com.Gr00ze.drones_mod.network.PacketHandler.sendToServer;

public class GenericControllerScreen extends UtilityScreen{
    private final int genericDroneId;
    private final GenericDrone genericDrone;


    public static float speedModifier = 0.078F * 5;



    public GenericControllerScreen(int genericDroneId, GenericDrone entity) {
        super(Component.literal("Titolo"));
        this.genericDroneId = genericDroneId;
        this.genericDrone = entity;
        this.height = 20;
        this.width = 20;



    }


    @Override
    protected void init() {
        super.init();
        int offsetY = 0;

        Dimension genericButtonDimension = new Dimension(61, 15);
        if (genericDrone == null){return;}
        for (int i = 0; i < 4; i++) {
            Point buttonPositionUp = new Point(width - 2 * genericButtonDimension.width,  offsetY);
            offsetY += genericButtonDimension.height;
            Point buttonPositionDown = new Point(width - genericButtonDimension.width , buttonPositionUp.y);

            int finalI = i;
            addButton("Up"+ (i + 1), button -> adjustMotors(new int[]{finalI},true), buttonPositionUp, genericButtonDimension, null);

            addButton("Down"+ (i + 1), button -> adjustMotors(new int[]{finalI},false), buttonPositionDown, genericButtonDimension, null);
        }



        Point allMotorsButtonPosition = new Point(width - 2 * genericButtonDimension.width, offsetY += 2);
        offsetY += 2;
        Point allMotorsButtonPositionDown = new Point(allMotorsButtonPosition.x + genericButtonDimension.width , allMotorsButtonPosition.y);


        // Logica per aumentare la velocità di tutti i motori contemporaneamente
        addButton("AllUp", button -> adjustMotors(new int[]{1,2,3,4},true), allMotorsButtonPosition, genericButtonDimension, null);

        // Logica per diminuire la velocità di tutti i motori contemporaneamente
        addButton("AllDown", button -> adjustMotors(new int[]{1,2,3,4},false), allMotorsButtonPositionDown, genericButtonDimension, null);

        Map<String, int[][]> angleMotorMap = new HashMap<>();
        angleMotorMap.put("Pitch", new int[][]{{1, 2}, {3, 4}});
        angleMotorMap.put("Yaw", new int[][]{{1, 3}, {2, 4}});
        angleMotorMap.put("Roll", new int[][]{{1, 4}, {2, 3}});

        for (Map.Entry<String, int[][]> entry : angleMotorMap.entrySet()) {
            String rotationName = entry.getKey();
            int[][] motorPairs = entry.getValue();

            Point angleButtonPositionUp = new Point(width - 2 * genericButtonDimension.width , offsetY += genericButtonDimension.height);
            Point angleButtonPositionDown = new Point(angleButtonPositionUp.x + genericButtonDimension.width, angleButtonPositionUp.y);
            addButton(rotationName + "Up", button -> {
                adjustMotors(motorPairs[0], true);
                adjustMotors(motorPairs[1], false);
            }, angleButtonPositionUp, genericButtonDimension, null);

            addButton(rotationName + "Down", button -> {
                adjustMotors(motorPairs[0], false);
                adjustMotors(motorPairs[1], true);
            }, angleButtonPositionDown, genericButtonDimension, null);
        }
    }

    public void adjustMotors(int[] motorIds, boolean positiveFactor){
        int factor_unit = positiveFactor ? 1 : - 1;
        for (int motorId : motorIds){
            float actualSpeed;
            switch (motorId){
                case 1: actualSpeed = this.genericDrone.getW1();break;
                case 2: actualSpeed = this.genericDrone.getW2();break;
                case 3: actualSpeed = this.genericDrone.getW3();break;
                case 4: actualSpeed = this.genericDrone.getW4();break;
                default: return;
            }
            ControllerPacket packet = new ControllerPacket(this.genericDroneId,motorId, actualSpeed + speedModifier * factor_unit);
            sendToServer(packet);
        }

    }


    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        //renderBackground(graphics, mouseX, mouseY, partialTicks);

        // Disegna il rettangolo con gradiente
        graphics.fillGradient(2, 2, 200, 200, 0xFF00FF, 0xFF0000);

        // Verifica se genericDrone è null
        if (genericDrone == null) {
            // Disegna il testo "NO DRONE SET"
            graphics.drawString(font, "NO DRONE SET", width / 2, height / 2, 0xFFFFFF);
        } else {
            int offsetY = 5;
            // Suggerimenti di velocità e angoli
            Vec3 speed = genericDrone.getDeltaMovement();
            String speedHint = String.format("Speed: x:%f y:%f z:%f", speed.x, speed.y, speed.z);
            String pitchHint = String.format("Pitch: %.2f", genericDrone.getPitchAngle());
            String rollHint = String.format("Roll: %.2f", genericDrone.getRollAngle());
            String yawHint = String.format("Yaw: %.2f", genericDrone.getYawAngle());

            graphics.drawString(font, speedHint, 5, offsetY, 0xFFFFFF);
            graphics.drawString(font, pitchHint, 5, offsetY += font.lineHeight, 0xFFFFFF);
            graphics.drawString(font, rollHint, 5, offsetY += font.lineHeight, 0xFFFFFF);
            graphics.drawString(font, yawHint, 5, offsetY += font.lineHeight, 0xFFFFFF);
        }

        // Itera attraverso gli elementi renderabili presenti nella GUI
        for (Renderable renderable : this.renderables) {
            renderable.render(graphics, mouseX, mouseY, partialTicks);
        }

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}