package com.Gr00ze.drones_mod.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;

public class HorseDroneTest extends Horse {
    public HorseDroneTest(EntityType<? extends Horse> p_30689_, Level p_30690_) {
        super(p_30689_, p_30690_);
    }


    public static AttributeSupplier getMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.JUMP_STRENGTH,20)
                .add(Attributes.MAX_HEALTH,200)
                .add(Attributes.MOVEMENT_SPEED,2)
                .build();
    }
}
