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
    private int genericDroneId;
    private GenericDrone genericDrone;

    private GuiEventListener buttonDown;
    private GuiEventListener buttonUp;

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

            this.buttonDown = addRenderableWidget(Button.builder(
                    Component.literal("+"),
                    this::buttonUP).bounds(10,10,20,20).build()
            );
            this.buttonUp = addRenderableWidget(Button.builder(
                    Component.literal("-"),
                    this::buttonDown).bounds(40,10,20,20).build()
            );

        }else{
            EditBox ebox = new EditBox(this.font,1,1,20,20,Component.literal("SS"));

            addRenderableWidget(ebox);
        }
        super.init();

    }

    public void buttonUP(Button button){
//        genericDrone.setW1(genericDrone.getW1() + 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        DebugPacket packet = new DebugPacket(this.genericDroneId, (int) (this.genericDrone.getW1()+1));
        DebugPacketHandler.CHANNEL.sendToServer(packet);
    }
    public void buttonDown(Button button){
//        genericDrone.setW1(genericDrone.getW1() - 1);;
//        System.out.println("WC= "+genericDrone.getW1());
        DebugPacket packet = new DebugPacket(this.genericDroneId, (int) (this.genericDrone.getW1()-1));
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