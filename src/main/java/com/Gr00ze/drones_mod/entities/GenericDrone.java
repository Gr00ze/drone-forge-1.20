package com.Gr00ze.drones_mod.entities;



import net.minecraft.client.Minecraft;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;

import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;


import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;


import static com.Gr00ze.drones_mod.DronesMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GenericDrone extends Mob{
    public static AnimationState spinRotor1,spinRotor2,spinRotor3,spinRotor4;
    static final EntityDataAccessor<Float>
             syncedAngularVelocity1 = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT),
             syncedAngularVelocity2 = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT),
             syncedAngularVelocity3 = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT),
             syncedAngularVelocity4 = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT),
             syncedSpeedRoll = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT),
             syncedSpeedYaw = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT),
             syncedSpeedPitch = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT),
             syncedPitchAngle = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT),
             syncedRollAngle = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT),
             syncedYawAngle = SynchedEntityData.defineId(GenericDrone.class, EntityDataSerializers.FLOAT);


    public float weight = 2;
    public float lastTickTime = 0,
            currentAltitude = (float) getY(),
            lastAltitude = (float) getY();
    static int MAX_HEALTH = 20;

    public float MAX_SPEED = 5;
    private boolean driverWantGoUp = false,driverWantGoDown = false;
    private float lastRollError = 0,rollErrorSum = 0.0F,
            lastPitchError = 0,pitchErrorSum = 0.0F,
            lastYawError = 0F,yawErrorSum = 0.0F,
            lastAltitudeError = 0F, altitudeErrorSum = 0.0F;
    private double trigonometricValue = 0;


    protected GenericDrone(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();

        spinRotor1 = new AnimationState();
        spinRotor2 = new AnimationState();
        spinRotor3 = new AnimationState();
        spinRotor4 = new AnimationState();
        spinRotor1.start(this.tickCount);
        spinRotor2.start(this.tickCount);
        spinRotor3.start(this.tickCount);
        spinRotor4.start(this.tickCount);

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

        calculatePhysic();

        calculateStabilization();

        calculateBoundingBox();

        calculateCollision();

        handlePassengers();




    }

    private void calculateStabilization() {
        float Kp = 0.1F;  // Costante proporzionale
        float Ki = 0.00000001F; // Costante integrale
        float Kd = 0.5F;   // Costante derivativa

        float targetRoll = 0.0F;
        float rollError = targetRoll - this.getRollAngle();
        rollErrorSum += rollError;
        float rollErrorRate = rollError - lastRollError;

        float targetPitch = 0.0F;
        float pitchError = targetPitch - this.getPitchAngle();
        pitchErrorSum += pitchError;
        float pitchErrorRate = pitchError - lastPitchError;

        float targetYaw = (float) this.getWantedYawAngle();
        float yawError = targetYaw - this.getYawAngle();
        if (Math.abs(yawError) > Math.PI) {
            if (yawError > 0) {
                yawError -= 2 * Mth.PI;
            } else {
                yawError += 2 * Mth.PI;
            }
        }

        yawErrorSum += yawError;
        float yawErrorRate = yawError - lastYawError;



        float balancingFactorYaw = Kp * yawError + Ki * yawErrorSum + Kd * yawErrorRate;
        float balancingFactorRoll = Kp * rollError + Ki * rollErrorSum + Kd * rollErrorRate;
        float balancingFactorPitch = Kp * pitchError + Ki * pitchErrorSum + Kd * pitchErrorRate;

        this.setW1(this.getW1() + balancingFactorRoll + balancingFactorPitch + balancingFactorYaw);
        this.setW2(this.getW2() - balancingFactorRoll + balancingFactorPitch - balancingFactorYaw);
        this.setW3(this.getW3() - balancingFactorRoll - balancingFactorPitch + balancingFactorYaw);
        this.setW4(this.getW4() + balancingFactorRoll - balancingFactorPitch - balancingFactorYaw);

        this.lastRollError = rollError;
        this.lastPitchError = pitchError;
        this.lastYawError = yawError;

     // Kp = 0.01F;
     // Ki = 0.000001F;
     // Kd = 0.001F;   //
     // float altitudeError = currentAltitude - lastAltitude;
     // altitudeErrorSum += altitudeError;
     // float altitudeErrorRate = altitudeError - lastAltitudeError;
     // float balancingFactorAltitude = Kp * altitudeError + Ki * altitudeErrorSum + Kd * altitudeErrorRate;
     // this.setW1(this.getW1() - balancingFactorAltitude);
     // this.setW2(this.getW2() - balancingFactorAltitude);
     // this.setW3(this.getW3() - balancingFactorAltitude);
     // this.setW4(this.getW4() - balancingFactorAltitude);
     // lastAltitude = currentAltitude;
     // currentAltitude = (float) this.getY();
    }



    private void handlePassengers() {
        List <Entity> passengers = this.getPassengers();
        int size = passengers.size();
        if (size == 0) return;
        Entity rider = passengers.get(0);
        if (rider instanceof Player playerRider && !playerRider.level().isClientSide()){
            //System.out.println("playerRider.yya: "+playerRider.yya);
            float incrementSpeed = 0.01F;

            this.setWantedYawAngle(rider.getYRot()*Mth.PI/180);
            if(driverWantGoUp){
                this.addW1(incrementSpeed);
                this.addW2(incrementSpeed);
                this.addW4(incrementSpeed);
                this.addW3(incrementSpeed);
            }else if(driverWantGoDown) {

                this.addW1(-incrementSpeed);
                this.addW2(-incrementSpeed);
                this.addW4(-incrementSpeed);
                this.addW3(-incrementSpeed);
            }
            //instant model rotation
            //this.setYawAngle(rider.getYRot()*Mth.PI/180);
            float degreesYawAngle = this.getYawAngle() * 180 / Mth.PI;

            if (rider.getYRot() % 360 - degreesYawAngle % 360 < 180) {
                //sx
                this.addW1(-incrementSpeed);
                this.addW2(+incrementSpeed);
                this.addW3(-incrementSpeed);
                this.addW4(+incrementSpeed);
            }else {
                this.addW1(+incrementSpeed);
                this.addW2(-incrementSpeed);
                this.addW3(+incrementSpeed);
                this.addW4(-incrementSpeed);
            }
            //forward back right left movement
            this.setW1(this.getW1() - playerRider.zza/20+ playerRider.xxa/20 ) ;
            this.setW2(this.getW2() - playerRider.zza/20- playerRider.xxa/20 );
            this.setW3(this.getW3() + playerRider.zza/20- playerRider.xxa/20 );
            this.setW4(this.getW4() + playerRider.zza/20+ playerRider.xxa/20 );

            //actual structure
            //2----1
            //3----4




        }
    }

    private void setWantedYawAngle(double trigonometricValue) {
        this.trigonometricValue = trigonometricValue % 360;
    }
    private double getWantedYawAngle() {
        return this.trigonometricValue;
    }


    private void calculateCollision() {
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(0, 0.2, 0));
        if (!list.isEmpty()) {

            for (Entity collidingEntity : list) {
                boolean isPassenger = false;
                for (Entity passenger : this.getPassengers()) {
                    isPassenger = passenger == collidingEntity;
                }
                if (isPassenger) continue;
                Vec3 vec = collidingEntity.getDeltaMovement();
                collidingEntity.setPos(collidingEntity.getX(),this.getY() + this.getBoundingBox().getYsize(),collidingEntity.getZ());
                collidingEntity.setDeltaMovement(vec.x,vec.y < 0 ? 0 : vec.y,vec.z);
                collidingEntity.resetFallDistance();
                collidingEntity.setOnGround(true);
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

    private void calculatePhysic() {

        calculateRotationAngle();

        long currentTickTime = this.tickCount; // Tempo attuale (tick corrente)
        float deltaTime = (currentTickTime - lastTickTime) * 0.05f; // Conversione da tick a secondi (20 tick per secondo)

        float totalForce = getW1() + getW2() + getW3() + getW4(),
        acceleration = totalForce/weight,
        ax = acceleration * (Mth.sin(this.getRollAngle()) * Mth.cos(this.getYawAngle()) + Mth.sin(this.getPitchAngle()) * Mth.sin(this.getYawAngle())),
        ay = acceleration * Mth.cos(this.getPitchAngle()) * Mth.cos(this.getRollAngle()),
        az = acceleration * (Mth.sin(-this.getPitchAngle()) * Mth.cos(this.getYawAngle()) + Mth.sin(this.getRollAngle()) * Mth.sin(this.getYawAngle()));
        Vector3f v1 = this.getDeltaMovement().toVector3f();
        float
        v2x = ax * deltaTime + v1.x,
        v2y = ay * deltaTime + v1.y,
        v2z = az * deltaTime + v1.z;
//        System.out.println(ax+" a"+ay+" "+az);
//        System.out.println(v1.x+" v1"+v1.y+" "+v1.z);
//        System.out.println(v2x+" v2"+v2y+" "+v2z);
        Vector3f v2 = new Vector3f(v2x,v2y,v2z);
        this.setDeltaMovement(v2.x,v2.y,v2.z);

        lastTickTime = currentTickTime;
    }

    public void calculateRotationAngle(){
        //actual structure
        //2----1
        //3----4
        float w1 = getW1(), w2 = getW2(), w3 = getW3(), w4 = getW4();
        float pitchSpeed = (( w2 + w1 ) - ( w3 + w4 )) ;
        float rollSpeed = (( w1 + w4 ) - ( w2 + w3 )) ;
        float yawSpeed = (( w1 + w3 ) - ( w2 + w4 ));
        this.setYawAngle(this.getYawAngle() + yawSpeed / 40);
        this.setRollAngle(this.getRollAngle() + rollSpeed / 40);
        this.setPitchAngle(this.getPitchAngle() + pitchSpeed / 40);
//        System.out.print("yaw :"+this.getYawAngle() * 180 / Mth.PI);
//        System.out.print("roll :"+this.getRollAngle() * 180 / Mth.PI);
//        System.out.println("pitch :"+this.getPitchAngle() * 180 / Mth.PI);

        this.setYRot((this.getYawAngle() * 180 / Mth.PI));





    };

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
        float initialSpeed = 0, initialAngle = 0;
        this.entityData.define(syncedAngularVelocity1, initialSpeed);
        this.entityData.define(syncedAngularVelocity2, initialSpeed);
        this.entityData.define(syncedAngularVelocity3, initialSpeed);
        this.entityData.define(syncedAngularVelocity4, initialSpeed);
        this.entityData.define(syncedPitchAngle, initialAngle);
        this.entityData.define(syncedRollAngle, initialAngle);
        this.entityData.define(syncedYawAngle, initialAngle);

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
    public float getYawAngle(){
        return this.entityData.get(syncedYawAngle);
    }
    public float getRollAngle(){
        return this.entityData.get(syncedRollAngle);
    }
    public float getPitchAngle(){
        return this.entityData.get(syncedPitchAngle);
    }

    public void setW1(float angularVelocity) {
        //System.out.println("Arrivato");
        //System.out.println(angularVelocity);
        this.entityData.set(syncedAngularVelocity1,angularVelocity > 0 ? angularVelocity < MAX_SPEED ? angularVelocity : 0 : 0);
    }
    public void setW2(float angularVelocity) {
        this.entityData.set(syncedAngularVelocity2,angularVelocity > 0 ? angularVelocity < MAX_SPEED ? angularVelocity : 0  : 0);
    }
    public void setW3(float angularVelocity) {
            this.entityData.set(syncedAngularVelocity3,angularVelocity > 0 ? angularVelocity < MAX_SPEED ? angularVelocity : 0  : 0);
    }
    public void setW4(float angularVelocity) {
            this.entityData.set(syncedAngularVelocity4,angularVelocity > 0 ? angularVelocity < MAX_SPEED ? angularVelocity : 0  : 0);
    }
    private void addW1(float angularVelocity) {
        this.setW1(this.getW1()+angularVelocity);
    }
    private void addW2(float angularVelocity) {
        this.setW2(this.getW2()+angularVelocity);
    }
    private void addW3(float angularVelocity) {
        this.setW3(this.getW3()+angularVelocity);
    }
    private void addW4(float angularVelocity) {
        this.setW4(this.getW4()+angularVelocity);
    }
    public void setYawAngle(float yawAngle){
        this.entityData.set(syncedYawAngle,yawAngle);
    }
    public void setPitchAngle(float pitchAngle){
        this.entityData.set(syncedPitchAngle,pitchAngle);
    }
    public void setRollAngle(float rollAngle){
        this.entityData.set(syncedRollAngle,rollAngle);
    }


    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event ){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        ItemStack itemStack = player.getMainHandItem();
        if (entity instanceof GenericDrone genericDrone && itemStack.isEmpty()){
            player.startRiding(entity);

        }
    }


    public void driverWantGoUp(boolean driverWantGoUp) {
        this.driverWantGoUp = driverWantGoUp;
    }


    public void driverWantGoDown(boolean driverWantGoDown) {
        this.driverWantGoDown = driverWantGoDown;
    }
}



