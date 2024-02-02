package com.Gr00ze.drones_mod.entities;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class AbstractDrone extends Mob {


    private static final EntityDataAccessor<Float>
        rotorSpeed1 = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        rotorSpeed2 = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        rotorSpeed3 = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        rotorSpeed4 = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        yawAngle = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        pitchAngle = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        rollAngle = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        weight = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT);


    protected AbstractDrone(EntityType<? extends Mob> mobType, Level level) {
        super(mobType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        float initialSpeed = 0, initialAngle = 0;
        this.entityData.define(rotorSpeed1, initialSpeed);
        this.entityData.define(rotorSpeed2, initialSpeed);
        this.entityData.define(rotorSpeed3, initialSpeed);
        this.entityData.define(rotorSpeed4, initialSpeed);
        this.entityData.define(pitchAngle, initialAngle);
        this.entityData.define(rollAngle, initialAngle);
        this.entityData.define(yawAngle, initialAngle);
    }

    public float getAngle(DroneAngle angle){
        return switch (angle) {
            case YAW -> this.entityData.get(yawAngle);
            case PITCH -> this.entityData.get(pitchAngle);
            default -> this.entityData.get(rollAngle);
        };
    }


    public float getMotorForce(int id){
        return switch (id) {
            case 1 -> this.entityData.get(rotorSpeed1);
            case 2 -> this.entityData.get(rotorSpeed2);
            case 3 -> this.entityData.get(rotorSpeed3);
            case 4 -> this.entityData.get(rotorSpeed4);
            default -> throw new IllegalArgumentException("Get: Invalid Motor id. Got: "+id);
        };
    }

    public void setMotorForce(int id,float rotorSpeed){
        float MAX_SPEED = 10,MIN_SPEED = 0;
        rotorSpeed = Math.max(MAX_SPEED,Math.min(MIN_SPEED,rotorSpeed));
        switch (id) {
            case 1 -> this.entityData.set(rotorSpeed1,rotorSpeed);
            case 3 -> this.entityData.set(rotorSpeed3,rotorSpeed);
            case 4 -> this.entityData.set(rotorSpeed4,rotorSpeed);
            case 2 -> this.entityData.set(rotorSpeed2,rotorSpeed);
            default -> throw new IllegalArgumentException("Set: Invalid Motor id. Got: "+id);
        };
    }

    public enum DroneAngle {
        ROLL, PITCH, YAW
    }

    public static AttributeSupplier getMobAttributes(){
        double MAX_HEALTH = 20;
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .build();
    }
}
