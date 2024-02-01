package com.Gr00ze.drones_mod.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;

public abstract class UtilityScreen extends Screen {
    protected UtilityScreen(Component component) {
        super(component);
    }

    public Button addButton(String name, Button.OnPress onPress,Point pos, Dimension dim, Tooltip tooltip){
        Button.Builder builder = new Button.Builder(Component.literal(name),onPress)
                .bounds(pos.x, pos.y, dim.width, dim.height);

        if (tooltip != null)
            builder = builder.tooltip(tooltip);

        return addRenderableWidget(builder.build());
    }

}
