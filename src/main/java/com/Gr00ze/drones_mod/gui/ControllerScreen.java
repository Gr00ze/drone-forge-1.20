package com.Gr00ze.drones_mod.gui;

import com.Gr00ze.drones_mod.entities.GenericDrone;
import com.Gr00ze.drones_mod.network.ControllerPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import static com.Gr00ze.drones_mod.network.ControllerPacketHandler.sendToServer;

public class ControllerScreen extends Screen{
    private final int genericDroneId;
    private final GenericDrone genericDrone;

    public static float speedModifier = 0.078F;



    //    public MyScreen(MyMenu menu, Inventory inv, Component title) {
//        super(menu, inv, title);
//    }
    public ControllerScreen(int genericDroneId, GenericDrone entity) {
        super(Component.literal("Titolo"));
        this.genericDroneId = genericDroneId;
        this.genericDrone = entity;
        this.height = 20;
        this.width = 20;



    }


    @Override
    protected void init() {
        if (genericDrone != null){
            Component c = Component.literal("Motore 1");
            EditBox editBox1 = new EditBox(this.font, 10, 40, 20, 20, c);
            editBox1.setResponder(newText -> {
                try {
                    float speed = Float.parseFloat(newText);
                    ControllerPacket packet = new ControllerPacket(this.genericDroneId,1, speed);
                    sendToServer(packet);
                }catch (NumberFormatException ignored){}
            });
            addRenderableWidget(editBox1);

            Component c2 = Component.literal("Motore 2");
            EditBox editBox2 = new EditBox(this.font, 40, 40, 20, 20, c);
            editBox2.setResponder(newText -> {
                try {
                    float speed = Float.parseFloat(newText);
                    ControllerPacket packet = new ControllerPacket(this.genericDroneId,2, speed);
                    sendToServer(packet);
                }catch (NumberFormatException ignored){}
            });


            addRenderableWidget(editBox2);

            Component c3 = Component.literal("Motore 3s");
            EditBox editBox3 = new EditBox(this.font, 80, 40, 20, 20, c);
            editBox3.setResponder(newText -> {
                try {
                    float speed = Float.parseFloat(newText);
                    ControllerPacket packet = new ControllerPacket(this.genericDroneId,3, speed);
                    sendToServer(packet);
                }catch (NumberFormatException ignored){}
            });
            addRenderableWidget(editBox3);

            Component c4 = Component.literal("Motore 4");
            EditBox editBox4 = new EditBox(this.font, 120, 40, 20, 20, c);
            editBox4.setResponder(newText -> {
                try {
                    float speed = Float.parseFloat(newText);
                    ControllerPacket packet = new ControllerPacket(this.genericDroneId,4, speed);
                    sendToServer(packet);
                }catch (NumberFormatException ignored){}
            });
            addRenderableWidget(editBox4);

            addRenderableWidget(Button.builder(
                    Component.literal("1+"),
                    this::buttonUP1).bounds(10,10,20,20).build()
            );
             addRenderableWidget(Button.builder(
                    Component.literal("1-"),
                    this::buttonDown1).bounds(40,10,20,20).build()
            );

            addRenderableWidget(Button.builder(
                    Component.literal("2+"),
                    this::buttonUP2).bounds(70,10,20,20).build()
            );
            addRenderableWidget(Button.builder(
                    Component.literal("2-"),
                    this::buttonDown2).bounds(100,10,20,20).build()
            );

            addRenderableWidget(Button.builder(
                    Component.literal("3+"),
                    this::buttonUP3).bounds(130,10,20,20).build()
            );
            addRenderableWidget(Button.builder(
                    Component.literal("3-"),
                    this::buttonDown3).bounds(160,10,20,20).build()
            );

            addRenderableWidget(Button.builder(
                    Component.literal("4+"),
                    this::buttonUP4).bounds(190,10,20,20).build()
            );
            addRenderableWidget(Button.builder(
                    Component.literal("4-"),
                    this::buttonDown4).bounds(220,10,20,20).build()
            );
            addRenderableWidget(Button.builder(
                    Component.literal("A+"),
                    this::buttonUpAll).bounds(250,10,20,20).build()
            );
            addRenderableWidget(Button.builder(
                    Component.literal("A-"),
                    this::buttonDownAll).bounds(280,10,20,20).build()
            );




        }else{
            EditBox ebox = new EditBox(this.font,1,1,20,20,Component.literal("SS"));

            addRenderableWidget(ebox);
        }
        super.init();

    }

    private void buttonUpAll(Button button) {
        ControllerPacket
        packet = new ControllerPacket(this.genericDroneId,1, (this.genericDrone.getW1()+speedModifier));
        sendToServer(packet);
        packet = new ControllerPacket(this.genericDroneId,2, (this.genericDrone.getW2()+speedModifier));
        sendToServer(packet);
        packet = new ControllerPacket(this.genericDroneId,3, (this.genericDrone.getW3()+speedModifier));
        sendToServer(packet);
        packet = new ControllerPacket(this.genericDroneId,4, (this.genericDrone.getW4()+speedModifier));
        sendToServer(packet);
    }

    private void buttonDownAll(Button button) {
        ControllerPacket
                packet = new ControllerPacket(this.genericDroneId,1, (this.genericDrone.getW1()-speedModifier));
        sendToServer(packet);
        packet = new ControllerPacket(this.genericDroneId,2, (this.genericDrone.getW2()-speedModifier));
        sendToServer(packet);
        packet = new ControllerPacket(this.genericDroneId,3, (this.genericDrone.getW3()-speedModifier));
        sendToServer(packet);
        packet = new ControllerPacket(this.genericDroneId,4, (this.genericDrone.getW4()-speedModifier));
        sendToServer(packet);
    }

    public void buttonUP1(Button button){
//        genericDrone.setW1(genericDrone.getW1() + 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        ControllerPacket packet = new ControllerPacket(this.genericDroneId,1, (this.genericDrone.getW1()+speedModifier));
        sendToServer(packet);
    }
    public void buttonDown1(Button button){
//        genericDrone.setW1(genericDrone.getW1() - 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        ControllerPacket packet = new ControllerPacket(this.genericDroneId,1, (this.genericDrone.getW1()-speedModifier));
        sendToServer(packet);

    }
    public void buttonUP2(Button button){
//        genericDrone.setW1(genericDrone.getW1() + 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        ControllerPacket packet = new ControllerPacket(this.genericDroneId,2, (this.genericDrone.getW2()+speedModifier));
        sendToServer(packet);
    }
    public void buttonDown2(Button button){
//        genericDrone.setW1(genericDrone.getW1() - 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        ControllerPacket packet = new ControllerPacket(this.genericDroneId,2, (this.genericDrone.getW2()-speedModifier));
        sendToServer(packet);

    }
    public void buttonUP3(Button button){
//        genericDrone.setW1(genericDrone.getW1() + 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        ControllerPacket packet = new ControllerPacket(this.genericDroneId, 3,  (this.genericDrone.getW3()+speedModifier));
        sendToServer(packet);
    }
    public void buttonDown3(Button button){
//        genericDrone.setW1(genericDrone.getW1() - 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        ControllerPacket packet = new ControllerPacket(this.genericDroneId, 3,  (this.genericDrone.getW3()-speedModifier));
        sendToServer(packet);

    }
    public void buttonUP4(Button button){
//        genericDrone.setW1(genericDrone.getW1() + 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        ControllerPacket packet = new ControllerPacket(this.genericDroneId, 4, (this.genericDrone.getW4()+speedModifier));
        sendToServer(packet);
    }
    public void buttonDown4(Button button){
//        genericDrone.setW1(genericDrone.getW1() - 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        ControllerPacket packet = new ControllerPacket(this.genericDroneId, 4, (this.genericDrone.getW4()-speedModifier));
        sendToServer(packet);

    }

    @Override
    public void tick() {
        super.tick();

        // Add ticking logic for EditBox in editBox
        //this.editBox.tick();
    }
    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        //this.renderBackground(graphics);
        graphics.fillGradient(2,2,200,200,0xFF00FF,0xFF0000);
        // Render things here before widgets (background textures)
        if(genericDrone==null) {


            graphics.drawString(this.font, "NO DRONE SET", width / 2, height / 2, 0xFFFFFF);
        }else{
            String text = String.format("p: %f \n r: %f \n y: %f", genericDrone.getPitchAngle(),genericDrone.getRollAngle(),genericDrone.getYawAngle());
            graphics.drawString(this.font, text , 5, 60, 0xFFFFFF);
        }
        // Then the widgets if this is a direct child of the Screen
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}