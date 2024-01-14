package com.Gr00ze.drones.gui;

import com.Gr00ze.drones.entities.GenericDrone;
import com.Gr00ze.drones.network.DebugPacket;
import com.Gr00ze.drones.network.DebugPacketHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class MyScreen extends Screen{
    private final int genericDroneId;
    private final GenericDrone genericDrone;

    public static float speedModifier = 0.078F;



    //    public MyScreen(MyMenu menu, Inventory inv, Component title) {
//        super(menu, inv, title);
//    }
    public MyScreen(int genericDroneId, GenericDrone entity) {
        super(Component.literal("Titolo"));
        this.genericDroneId = genericDroneId;
        this.genericDrone = entity;
        this.height = 20;
        this.width = 20;



    }


    @Override
    protected void init() {
        if (genericDrone != null){

            addRenderableWidget(Button.builder(
                    Component.literal("+"),
                    this::buttonUP1).bounds(10,10,20,20).build()
            );
             addRenderableWidget(Button.builder(
                    Component.literal("-"),
                    this::buttonDown1).bounds(40,10,20,20).build()
            );

            addRenderableWidget(Button.builder(
                    Component.literal("+"),
                    this::buttonUP2).bounds(70,10,20,20).build()
            );
            addRenderableWidget(Button.builder(
                    Component.literal("-"),
                    this::buttonDown2).bounds(100,10,20,20).build()
            );

            addRenderableWidget(Button.builder(
                    Component.literal("+"),
                    this::buttonUP3).bounds(130,10,20,20).build()
            );
            addRenderableWidget(Button.builder(
                    Component.literal("-"),
                    this::buttonDown3).bounds(160,10,20,20).build()
            );

            addRenderableWidget(Button.builder(
                    Component.literal("+"),
                    this::buttonUP4).bounds(190,10,20,20).build()
            );
            addRenderableWidget(Button.builder(
                    Component.literal("-"),
                    this::buttonDown4).bounds(220,10,20,20).build()
            );


        }else{
            EditBox ebox = new EditBox(this.font,1,1,20,20,Component.literal("SS"));

            addRenderableWidget(ebox);
        }
        super.init();

    }

    public void buttonUP1(Button button){
//        genericDrone.setW1(genericDrone.getW1() + 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        DebugPacket packet = new DebugPacket(this.genericDroneId,1, (this.genericDrone.getW1()+speedModifier));
        DebugPacketHandler.CHANNEL.sendToServer(packet);
    }
    public void buttonDown1(Button button){
//        genericDrone.setW1(genericDrone.getW1() - 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        DebugPacket packet = new DebugPacket(this.genericDroneId,1, (this.genericDrone.getW1()-speedModifier));
        DebugPacketHandler.CHANNEL.sendToServer(packet);

    }
    public void buttonUP2(Button button){
//        genericDrone.setW1(genericDrone.getW1() + 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        DebugPacket packet = new DebugPacket(this.genericDroneId,2, (this.genericDrone.getW2()+speedModifier));
        DebugPacketHandler.CHANNEL.sendToServer(packet);
    }
    public void buttonDown2(Button button){
//        genericDrone.setW1(genericDrone.getW1() - 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        DebugPacket packet = new DebugPacket(this.genericDroneId,2, (this.genericDrone.getW2()-speedModifier));
        DebugPacketHandler.CHANNEL.sendToServer(packet);

    }
    public void buttonUP3(Button button){
//        genericDrone.setW1(genericDrone.getW1() + 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        DebugPacket packet = new DebugPacket(this.genericDroneId, 3,  (this.genericDrone.getW3()+speedModifier));
        DebugPacketHandler.CHANNEL.sendToServer(packet);
    }
    public void buttonDown3(Button button){
//        genericDrone.setW1(genericDrone.getW1() - 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        DebugPacket packet = new DebugPacket(this.genericDroneId, 3,  (this.genericDrone.getW3()-speedModifier));
        DebugPacketHandler.CHANNEL.sendToServer(packet);

    }
    public void buttonUP4(Button button){
//        genericDrone.setW1(genericDrone.getW1() + 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        DebugPacket packet = new DebugPacket(this.genericDroneId, 4, (this.genericDrone.getW4()+speedModifier));
        DebugPacketHandler.CHANNEL.sendToServer(packet);
    }
    public void buttonDown4(Button button){
//        genericDrone.setW1(genericDrone.getW1() - 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        DebugPacket packet = new DebugPacket(this.genericDroneId, 4, (this.genericDrone.getW4()-speedModifier));
        DebugPacketHandler.CHANNEL.sendToServer(packet);

    }

    @Override
    public void tick() {
        super.tick();

        // Add ticking logic for EditBox in editBox
        //this.editBox.tick();
    }
    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        graphics.fillGradient(2,2,200,200,0xFF00FF,0xFF0000);
        // Render things here before widgets (background textures)
        if(genericDrone==null){


            graphics.drawString(this.font,"NO DRONE SET",width/2,height/2,0xFFFFFF);

        }
        // Then the widgets if this is a direct child of the Screen
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}