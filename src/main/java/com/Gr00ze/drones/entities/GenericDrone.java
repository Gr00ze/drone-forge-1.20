package com.Gr00ze.drones.entities;

import com.google.common.eventbus.Subscribe;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;


import static com.Gr00ze.drones.entities.GenericDroneModel.motorRotations;

public class GenericDrone extends Mob {

    static final EntityDataAccessor<Float> sync = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT);

    public float weight = 2;
    public float lastTickTime = 0;
    static int MAX_HEALTH = 20;
    protected GenericDrone(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        motorRotations.start(this.tickCount);
        motorRotations.isStarted();
        System.out.println("added to world ");
    }

    @Override
    public boolean hurt(@NotNull DamageSource p_21016_, float p_21017_) {

        setW1(getW1()+1);

        return super.hurt(p_21016_, p_21017_);
    }



    @Override
    protected void registerGoals() {
        super.registerGoals();

    }

    @Override
    public void tick() {
        super.tick();
        float velocity = 0;

        long currentTickTime = this.tickCount; // Tempo attuale (tick corrente)
        float deltaTime = (currentTickTime - lastTickTime) * 0.05f; // Conversione da tick a secondi (20 tick per secondo)

        // Calcola l'accelerazione verticale usando a = F / m
        float verticalAcceleration = getW1() / weight;

        double verticalSpeed = this.getDeltaMovement().y();
        // Calcola la nuova velocità verticale utilizzando Vf = Vo + at
        float newVerticalSpeed = (float) ((verticalAcceleration * deltaTime) + verticalSpeed);

        // Applica il movimento verticale in base alla nuova velocità
        this.setDeltaMovement(this.getDeltaMovement().x(), newVerticalSpeed, this.getDeltaMovement().z());

        // Aggiorna la velocità verticale iniziale per il prossimo tick
        verticalSpeed = newVerticalSpeed;

        // Aggiorna il tempo dell'ultimo tick
        lastTickTime = currentTickTime;

        float width = 3;
        float height = 1;
        this.setBoundingBox(new AABB(
                this.getX()-width/2,
                this.getY(),
                this.getZ()-width/2,
                this.getX()+width/2,
                this.getY()+height,
                this.getZ()+width/2) );


    }

    public static AttributeSupplier getMobAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .build();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        float w1i = 0;
        this.entityData.define(sync, w1i);

    }

    public float getW1() {
        return this.entityData.get(sync);
    }

    public void setW1(float w1) {
        this.entityData.set(sync,w1);
    }
}



