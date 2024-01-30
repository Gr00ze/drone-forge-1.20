package com.Gr00ze.drones_mod.gui;

import com.Gr00ze.drones_mod.entities.GenericDrone;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class InfoScreen extends Screen {
    private GenericDrone genericDrone;

    protected InfoScreen(Component component) {
        super(component);
    }
    public InfoScreen(GenericDrone entity) {
        this(Component.literal("Titolo"));
        this.genericDrone = entity;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if(genericDrone==null) {
            graphics.drawString(this.font, "NO DRONE SET", width / 2, height / 2, 0xFFFFFF);
        }else{
            String text = String.format("p: %f \n r: %f \n y: %f", genericDrone.getPitchAngle(),genericDrone.getRollAngle(),genericDrone.getYawAngle());
            graphics.drawString(this.font, text , 5, 60, 0xFFFFFF);
        }
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


}
