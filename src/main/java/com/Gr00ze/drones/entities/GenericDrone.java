package com.Gr00ze.drones.entities;


import net.minecraft.client.Minecraft;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;


import static com.Gr00ze.drones.entities.GenericDroneModel.motorRotations;
@Mod.EventBusSubscriber(modid = "drone_mod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GenericDrone extends Mob{

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



        return super.hurt(p_21016_, p_21017_);
    }




    protected void registerGoals() {


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

        //rotation
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
    protected int calculateFallDamage(float p_21237_, float p_21238_) {
        double fallDamage = this.getDeltaMovement().y;
        System.out.println(this.getDeltaMovement().y);
        System.out.println(fallDamage);
        return (int)Math.abs(fallDamage);
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

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event ){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        ItemStack itemStack = player.getMainHandItem();
        boolean isDrone = entity instanceof GenericDrone;
        if (isDrone && itemStack.isEmpty()){
            player.startRiding(entity);
        }
    }
    @SubscribeEvent
    public static void onWheelScroll(InputEvent.Key event){
        Player player = Minecraft.getInstance().player;
        System.out.println(event.getKey());
        System.out.println(GLFW.GLFW_KEY_LEFT_CONTROL);
        if (player == null) return;
        Entity entity = player.getVehicle();
        if (!(entity instanceof GenericDrone)) return;
        System.out.println(entity);

    }
}



