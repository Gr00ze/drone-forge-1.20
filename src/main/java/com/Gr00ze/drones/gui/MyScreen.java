package com.Gr00ze.drones.gui;

import com.Gr00ze.drones.entities.GenericDrone;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.extensions.IForgeFont;
import org.jetbrains.annotations.NotNull;

public class MyScreen extends Screen{
    private final GenericDrone genericDrone;
    private GenericDrone genericDroneServer;
    private MyScreen editBox;
    private GuiEventListener button;

    //    public MyScreen(MyMenu menu, Inventory inv, Component title) {
//        super(menu, inv, title);
//    }
    public MyScreen(GenericDrone genericDrone) {
        super(Component.literal("Titolo"));
        this.genericDrone = genericDrone;
        this.height = 20;
        this.width = 20;

    }


    @Override
    protected void init() {
        super.init();
        // Aggiungi qui i widget e gli elementi della GUI
        this.button = addRenderableWidget(Button.builder(
                Component.literal("butt"),
                this::button).bounds(10,10,20,50).build()
        );
    }

    public void button(Button button){
        genericDrone.setW1(genericDrone.getW1() != 0 ? 0 : 1);;
        System.out.println("WC= "+genericDrone.getW1());

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
        graphics.drawString(this.font,"CIAO",30,0,0);
        // Then the widgets if this is a direct child of the Screen
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}