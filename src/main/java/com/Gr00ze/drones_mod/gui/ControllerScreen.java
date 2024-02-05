package com.Gr00ze.drones_mod.gui;

import com.Gr00ze.drones_mod.entities.AbstractDrone;
import com.Gr00ze.drones_mod.entities.GenericDrone;
import com.Gr00ze.drones_mod.entities.controllers.PIDController;
import com.Gr00ze.drones_mod.network.ControllerPacket;
import com.Gr00ze.drones_mod.network.ControllerPacket2;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static com.Gr00ze.drones_mod.network.PacketHandler.CHANNEL;
import static com.Gr00ze.drones_mod.network.PacketHandler.sendToServer;

public class ControllerScreen extends UtilityScreen{
    private final int droneId;
    private final AbstractDrone drone;


    public static float speedModifier = 0.078F * 5;



    public ControllerScreen(int droneId, AbstractDrone drone) {
        super(Component.literal("Titolo"));
        this.droneId = droneId;
        this.drone = drone;
        this.height = 20;
        this.width = 20;



    }


    @Override
    protected void init() {
        super.init();
        int offsetY = 0;

        Dimension genericButtonDimension = new Dimension(61, 15);
        if (drone == null){return;}
        for (int i = 0; i < 4; i++) {
            Point buttonPositionUp = new Point(width - 2 * genericButtonDimension.width,  offsetY);
            offsetY += genericButtonDimension.height;
            Point buttonPositionDown = new Point(width - genericButtonDimension.width , buttonPositionUp.y);

            int finalI = i+1;
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

        PIDController[] allControllers = drone.getAllPIDControllers();
        int numberOfControllers = allControllers.length;


        for (int i = 0; i < numberOfControllers; i++) {
            if (allControllers[i] == null) {
                continue;  // Salta il controller se è nullo
            }
            offsetY += genericButtonDimension.height;
            PIDController.PIDParameter[] parameters = PIDController.PIDParameter.values();
            for (int j = 0; j < parameters.length; j++) {
                Point editBoxPosition = new Point(width - 2 * genericButtonDimension.width + j * genericButtonDimension.width * 2/3, offsetY);
                EditBox editBox = addEmptyEditBox(font, editBoxPosition.x, editBoxPosition.y, genericButtonDimension.width * 2/3, genericButtonDimension.height);
                int finalJ = j;
                int finalI = i;
                editBox.setResponder((text)->{
                    if (!text.endsWith("F")) return;
                    try {
                        float value = Float.parseFloat(text.substring(0,text.length()-2));
                        sendToServer(new ControllerPacket2(droneId, finalI, parameters[finalJ], value));

                    } catch (NumberFormatException e){
                        System.out.printf("Invalid value given: %s\n", text);
                    }
                });

                Double parameterValue = allControllers[i].getParameter(parameters[j]);
                if (parameterValue != null) {
                    editBox.setValue(String.format("%.2e", parameterValue));
                }

                final int index = i;  // Deve essere effettivamente "final" o "effectively final" per essere utilizzato nel lambda
                final PIDController.PIDParameter param = parameters[j];


                // Aggiorna la posizione per la prossima EditBox
                editBoxPosition.x += genericButtonDimension.width * 2 / 3;
            }



        }

    }

    public void adjustMotors(int[] motorIds, boolean positiveFactor){
        int factor_unit = positiveFactor ? 1 : - 1;
        for (int motorId : motorIds){
            float actualSpeed = this.drone.getMotorForce(motorId);
            float rotorSpeed = actualSpeed + speedModifier * factor_unit;
            System.out.printf("Sending %f to motor %d%n", rotorSpeed, motorId);
            ControllerPacket packet = new ControllerPacket(this.droneId,motorId, rotorSpeed);
            sendToServer(packet);
        }

    }

    @Override
    protected boolean shouldNarrateNavigation() {
        return false;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        //renderBackground(graphics, mouseX, mouseY, partialTicks);

        // Disegna il rettangolo con gradiente
        graphics.fillGradient(2, 2, 200, 200, 0xFF00FF, 0xFF0000);

        // Verifica se genericDrone è null
        if (drone == null) {
            // Disegna il testo "NO DRONE SET"
            graphics.drawString(font, "NO DRONE SET", width / 2, height / 2, 0xFFFFFF);
        } else {
            int offsetY = 5;
            // Suggerimenti di velocità e angoli
            float[] motorSpeeds = drone.getAllMotorForce();
            Vec3 speed = drone.getDeltaMovement();
            PIDController[] controllers = drone.getAllPIDControllers();
            String motorSpeed = String.format("Speed: m1:%f m2:%f m3:%f m4:%f", motorSpeeds[0], motorSpeeds[1], motorSpeeds[2],motorSpeeds[3]);
            String speedHint = String.format("Speed: x:%f y:%f z:%f", speed.x, speed.y, speed.z);
            String pitchHint = String.format("Pitch: %.2f", drone.getAngle(AbstractDrone.DroneAngle.PITCH));
            String rollHint = String.format("Roll: %.2f", drone.getAngle(AbstractDrone.DroneAngle.ROLL));
            String yawHint = String.format("Yaw: %.2f", drone.getAngle(AbstractDrone.DroneAngle.YAW));
            StringBuilder kParameters = new StringBuilder();
            for (int i = 0; i < controllers.length; i++) {
                if (controllers[i]==null)continue;
                kParameters.append(String.format("%d: kp: %.2e kd: %.2e kp: %.2e\n", i, controllers[i].Kp, controllers[i].Ki, controllers[i].Kd));
            }


            graphics.drawString(font, motorSpeed, 5, offsetY, 0xFFFFFF);
            graphics.drawString(font, speedHint, 5, offsetY+= font.lineHeight, 0xFFFFFF);
            graphics.drawString(font, pitchHint, 5, offsetY += font.lineHeight, 0xFFFFFF);
            graphics.drawString(font, rollHint, 5, offsetY += font.lineHeight, 0xFFFFFF);
            graphics.drawString(font, yawHint, 5, offsetY += font.lineHeight, 0xFFFFFF);
            graphics.drawString(font, kParameters.toString(), 5, offsetY += font.lineHeight, 0xFFFFFF);
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