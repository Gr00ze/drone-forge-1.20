package com.Gr00ze.drones_mod.entities;

import com.Gr00ze.drones_mod.entities.controllers.PIDController;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import static com.Gr00ze.drones_mod.DronesMod.printDebug;

public abstract class AbstractDrone extends Mob {


    private static final EntityDataAccessor<Float>
        rotorSpeed1 = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        rotorSpeed2 = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        rotorSpeed3 = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        rotorSpeed4 = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        yawAngle = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        pitchAngle = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        rollAngle = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT),
        weightData = SynchedEntityData.defineId(AbstractDrone.class, EntityDataSerializers.FLOAT);

    protected float lastTickTime, targetYaw = 0;

    public AnimationState spinRotor1,spinRotor2,spinRotor3,spinRotor4;

    PIDController yawPID,rollPID,pitchPID,verticalPID;

    public AbstractDrone(EntityType<? extends Mob> mobType, Level level) {
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
        this.entityData.define(weightData, 2F);
    }


    public void setAngle(DroneAngle angleType, float value){
        switch (angleType) {
            case YAW -> this.entityData.set(yawAngle, value);
            case PITCH -> this.entityData.set(pitchAngle, value);
            case ROLL -> this.entityData.set(rollAngle, value);
        };
    }
    public float getAngle(DroneAngle angle){
        return switch (angle) {
            case YAW -> this.entityData.get(yawAngle);
            case PITCH -> this.entityData.get(pitchAngle);
            default -> this.entityData.get(rollAngle);
        };
    }

    public void addAngle(DroneAngle angleType, float value){
        setAngle(angleType,getAngle(angleType) + value);
    }

    public void setMotorForce(int id,float rotorSpeed){
        float MAX_SPEED = 10,MIN_SPEED = 0;
        rotorSpeed = Math.min(MAX_SPEED,Math.max(MIN_SPEED,rotorSpeed));
        switch (id) {
            case 1 -> this.entityData.set(rotorSpeed1,rotorSpeed);
            case 3 -> this.entityData.set(rotorSpeed3,rotorSpeed);
            case 4 -> this.entityData.set(rotorSpeed4,rotorSpeed);
            case 2 -> this.entityData.set(rotorSpeed2,rotorSpeed);
            default -> throw new IllegalArgumentException("Set: Invalid Motor id. Got: "+id);
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

    public void addMotorForce(int id,double rotorSpeed){
        this.setMotorForce(id, (float) (this.getMotorForce(id) + rotorSpeed));
    }

    public float[] getAllMotorForce(){
        return new float[]{
                this.entityData.get(rotorSpeed1),
                this.entityData.get(rotorSpeed2),
                this.entityData.get(rotorSpeed3),
                this.entityData.get(rotorSpeed4)
        };
    };

    public float getTotalForce(){
        float totalForce = 0;
        for (float force : getAllMotorForce()){
            totalForce += force;
        }
        return totalForce;
    }

    public void setWeight(float weight){
        if (weight > 0){
            this.entityData.set(weightData, weight);
        }
    };
    public float getWeight(){
    return this.entityData.get(weightData);
    };

    public enum DroneAngle {
        ROLL, PITCH, YAW
    }

    public static AttributeSupplier getMobAttributes(){
        double MAX_HEALTH = 20;
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .build();
    }

    public abstract void onEntityInteract(PlayerInteractEvent.EntityInteract event);

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        //TO DO dmg should be based on acceleration
        getAllMotorForce();
        return 1;
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

        //yawPID = new PIDController( 0.1F,0.00000001F,0.5F);
        rollPID = new PIDController( 0.1F,0.00000001F,0.5F);
        pitchPID = new PIDController( 0.1F,0.00000001F, 0.5F);
        verticalPID = new PIDController( 0.01F,0F, 0.5F);

        printDebug(this.level().isClientSide,"Added Drone");
    }


    @Override
    public void tick() {

        double yawSignal = 0, rollSignal = 0, pitchSignal = 0, verticalSignal = 0;

        if (yawPID != null){
            yawSignal = yawPID.calculate(targetYaw,this.getAngle(DroneAngle.YAW));
        }
        if (rollPID != null){
            rollSignal = rollPID.calculate(0,this.getAngle(DroneAngle.ROLL));
        }
        if (pitchPID != null){
            pitchSignal = pitchPID.calculate(0,this.getAngle(DroneAngle.PITCH));
        }
        if (verticalPID != null){
            verticalSignal = verticalPID.calculate(0.0F, (float) this.getDeltaMovement().y());
        }
        this.addMotorForce(1,+ rollSignal + pitchSignal + yawSignal + verticalSignal);
        this.addMotorForce(2,- rollSignal + pitchSignal - yawSignal + verticalSignal);
        this.addMotorForce(3,- rollSignal - pitchSignal + yawSignal + verticalSignal);
        this.addMotorForce(4,+ rollSignal - pitchSignal - yawSignal + verticalSignal);
        super.tick();

    }

    public PIDController[] getAllPIDControllers(){
        return new PIDController[]{yawPID,rollPID,pitchPID,verticalPID};
    }

    public void setKParameter(double value, PIDController.PIDParameter parameterType, int controllerId) {
        //controllerId should be an enum
        switch (controllerId){
            case 0: yawPID.set(value,parameterType);break;
            case 1: rollPID.set(value,parameterType);break;
            case 2: pitchPID.set(value,parameterType);break;
            case 3: verticalPID.set(value,parameterType);break;
        }
        printDebug(this.level().isClientSide(),"SETTER: ControllerId: %d Parameter %s set %.2e",controllerId, parameterType, value);
    }
}
