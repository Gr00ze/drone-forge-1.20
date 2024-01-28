package com.Gr00ze.drones.items;

import net.minecraft.world.item.Item;

public abstract class ItemVoid extends Item {
    public ItemVoid() {
        super(new Item.Properties());
    }

    public ItemVoid(Properties properties) {
        super(properties);
    }
}
