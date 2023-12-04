package com.Gr00ze.drones.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class GenericDrone extends Mob {


    protected GenericDrone(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }
}
