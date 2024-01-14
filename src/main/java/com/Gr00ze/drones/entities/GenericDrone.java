package com.Gr00ze.drones.entities;



import net.minecraft.client.Minecraft;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;

import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;


import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.Gr00ze.drones.DronesMod.MOD_ID;
import static com.Gr00ze.drones.client.GenericDroneModel.*;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GenericDrone extends Mob{

    static final EntityDataAccessor<Float>
             syncedAngularVelocity1 = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT),
             syncedAngularVelocity2 = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT),
             syncedAngularVelocity3 = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT),
             syncedAngularVelocity4 = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT);

    public float weight = 2;
    public float lastTickTime = 0;
    static int MAX_HEALTH = 20;
    private boolean isAcceleratingY;

    protected GenericDrone(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        spinRotor1.start(this.tickCount);
        spinRotor1.isStarted();
        spinRotor2.start(this.tickCount);
        spinRotor2.isStarted();
        spinRotor3.start(this.tickCount);
        spinRotor3.isStarted();
        spinRotor4.start(this.tickCount);
        spinRotor4.isStarted();

        System.out.println("added to world ");
    }

    @Override
    public boolean hurt(@NotNull DamageSource p_21016_, float p_21017_) {



        return super.hurt(p_21016_, p_21017_);
    }




    protected void registerGoals() {


    }
    @Override
    public void push(@NotNull Entity entity) {
//        if (entity instanceof Entity) {
//            if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
//                super.push(entity);
//            }
//        } else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
//            super.push(entity);
//        }

    }




    @Override
    public void tick() {
        super.tick();

        calculateVerticalSpeed();

        calculateBoundingBox();

        //calculateCollision();



    }

    private void calculateCollision() {
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(0.20000000298023224, -0.009999999776482582, 0.20000000298023224), EntitySelector.pushableBy(this));
        if (!list.isEmpty()) {

            for(int j = 0; j < list.size(); ++j) {
                Entity entity = list.get(j);
                if (entity instanceof LivingEntity livingEntity && livingEntity.getControlledVehicle()==this)
                    continue;
                entity.setPos(entity.getBlockX(),entity.getBlockY()+getBoundingBox().getYsize(),entity.getBlockZ());
            }
        }
    }

    private void calculateBoundingBox() {
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

    private void calculateVerticalSpeed() {
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
        float initialAngularVelocity = 0;
        this.entityData.define(syncedAngularVelocity1, initialAngularVelocity);
        this.entityData.define(syncedAngularVelocity2, initialAngularVelocity);
        this.entityData.define(syncedAngularVelocity3, initialAngularVelocity);
        this.entityData.define(syncedAngularVelocity4, initialAngularVelocity);

    }

    public float getW1() {
        return this.entityData.get(syncedAngularVelocity1);
    }
    public float getW2() {
        return this.entityData.get(syncedAngularVelocity2);
    }
    public float getW3() {
        return this.entityData.get(syncedAngularVelocity3);
    }
    public float getW4() {
        return this.entityData.get(syncedAngularVelocity4);
    }

    public void setW1(float w1) {
        System.out.println("Arrivato");
        System.out.println(w1);
        this.entityData.set(syncedAngularVelocity1,w1);
    }
    public void setW2(float angularVelocity) {
        this.entityData.set(syncedAngularVelocity2,angularVelocity);
    }
    public void setW3(float angularVelocity) {
            this.entityData.set(syncedAngularVelocity3,angularVelocity);
    }
    public void setW4(float angularVelocity) {
            this.entityData.set(syncedAngularVelocity4,angularVelocity);
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
    public static void onLeftControlPress(InputEvent.Key event){
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        Entity entity = player.getVehicle();
        if (!(entity instanceof GenericDrone genericDrone)) return;

//        if (event.getKey() == GLFW.GLFW_KEY_LEFT_CONTROL) {
//            DebugPacket packet = new DebugPacket(genericDrone.getId(), (int) (genericDrone.getW1() + 1));
//            DebugPacketHandler.CHANNEL.sendToServer(packet);
//        }
//        if( GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS){
//           System.out.println("Control premuto");
//        }else if (GLFW.glfwGetKey(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_CONTROL) == 0){
//           System.out.println("Control rilasciato");
//            DebugPacket packet = new DebugPacket(genericDrone.getId(), 0);
//            DebugPacketHandler.CHANNEL.sendToServer(packet);
//        }
    }

    public void isAcceleratingY(boolean isAcceleratingY) {
        this.isAcceleratingY = isAcceleratingY;
    }


}



