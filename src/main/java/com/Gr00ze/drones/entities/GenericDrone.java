package com.Gr00ze.drones.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class GenericDrone extends Mob {

    static int MAX_HEALT = 20;
    protected GenericDrone(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }




    public static AttributeSupplier getMobAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,MAX_HEALT)
                .build();
    }



}



