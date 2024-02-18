package com.Gr00ze.drones_mod.entities;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.List;

import static com.Gr00ze.drones_mod.entities.AbstractDrone.DroneAngle.YAW;
import static com.Gr00ze.drones_mod.entities.AbstractDrone.DroneAngle.PITCH;
import static com.Gr00ze.drones_mod.entities.AbstractDrone.DroneAngle.ROLL;

public class Drone extends AbstractDrone{



    public Drone(EntityType<? extends Mob> mobType, Level level) {
        super(mobType, level);
    }



    @Override
    public void tick() {
        super.tick();
        manageCollisions();
        resizeBoundingBox();
        calculateRotationAngle();
        calculateMovement();
        calculatePilotInput();

    }

    private void manageCollisions(){
        AABB baseBox = this.getBoundingBox();
        AABB biggerBox = baseBox.inflate(0, 0.2, 0);
        AABB topBox = baseBox.deflate(0, baseBox.getYsize() - 0.2,0).move(0, 0.4,0);
        AABB bottomBox = baseBox;
        List<Entity> collidingEntities = level().getEntities(this, topBox);
        if(collidingEntities.isEmpty())return;
        for (Entity collidingEntity : collidingEntities) {
            boolean isPassenger = false;
            for (Entity passenger : this.getPassengers()) {
                isPassenger = passenger == collidingEntity;
            }
            if (isPassenger) continue;
            Vec3 vec = collidingEntity.getDeltaMovement();
            collidingEntity.setPos(collidingEntity.getX(),topBox.maxY,collidingEntity.getZ());
            collidingEntity.setDeltaMovement(vec.x,vec.y < 0 ? 0 : vec.y,vec.z);
            collidingEntity.resetFallDistance();
            collidingEntity.setOnGround(true);

        }

    }
    private void resizeBoundingBox() {

    }

    private void calculateMovement() {
        long currentTickTime = this.tickCount; // Tempo attuale (tick corrente)
        float deltaTime = (currentTickTime - lastTickTime) * 0.05f; // Conversione da tick a secondi (20 tick per secondo)

        float totalForce = getTotalForce(),
                acceleration = totalForce / getWeight(),
                yaw = this.getAngle(YAW),
                pitch = this.getAngle(PITCH),
                roll = this.getAngle(ROLL),
                ax = acceleration * (Mth.sin(roll) * Mth.cos(yaw) + Mth.sin(pitch) * Mth.sin(yaw)),
                ay = acceleration * Mth.cos(pitch) * Mth.cos(roll),
                az = acceleration * (Mth.sin(-pitch) * Mth.cos(yaw) + Mth.sin(roll) * Mth.sin(yaw));
        Vector3f v1 = this.getDeltaMovement().toVector3f();
        float
                v2x = ax * deltaTime + v1.x,
                v2y = ay * deltaTime + v1.y,
                v2z = az * deltaTime + v1.z;

        Vector3f v2 = new Vector3f(v2x,v2y,v2z);
        this.setDeltaMovement(v2.x,v2.y,v2.z);

        lastTickTime = currentTickTime;
    }
    public void calculateRotationAngle(){
        float
                f1 = getMotorForce(1),
                f2 = getMotorForce(2),
                f3 = getMotorForce(3),
                f4 = getMotorForce(4),
                pitchSpeed = (( f2 + f1 ) - ( f3 + f4 )),
                rollSpeed = (( f1 + f4 ) - ( f2 + f3 )),
                yawSpeed = (( f1 + f3 ) - ( f2 + f4 ));
        this.addAngle(YAW, yawSpeed / 40);
        this.addAngle(ROLL,  rollSpeed / 40);
        this.addAngle(PITCH,pitchSpeed / 40);
        float polarAngle = (this.getAngle(YAW) * 180 / Mth.PI);
        this.setYRot(polarAngle);
        //yBodyRot = polarAngle;
    }

    private void calculatePilotInput() {
        List <Entity> passengers = this.getPassengers();
        int size = passengers.size();
        if (size == 0) return;
        Entity rider = passengers.get(0);
        if (rider instanceof Player playerRider && !playerRider.level().isClientSide()){
            float incrementSpeed = 0.01F;

            //range Player.getYRot: [0 - 360)
            //range targetYaw: (-inf, + inf);



            int k = 0;
            float radiantRiderYRot = rider.getYRot() * Mth.PI / 180;
            if(targetYaw!=null){
               k = (int) (targetYaw / (2 *Mth.PI));
               if (targetYaw % (2 * Mth.PI) - radiantRiderYRot > Mth.PI){
                   targetYaw = targetYaw + ((2 * Mth.PI) - (targetYaw - radiantRiderYRot)) + 2 * k;
               }else targetYaw = radiantRiderYRot + 2 * k * Mth.PI;
            }else targetYaw = radiantRiderYRot;
            //ccc









            //this.setYRot(rider.getYRot());
            float degreesYawAngle = this.getAngle(YAW) * 180 / Mth.PI;

//            if (rider.getYRot() % 360 - degreesYawAngle % 360 < 180) {
//                //sx
//                this.addMotorForce(1,-incrementSpeed);
//                this.addMotorForce(2,+incrementSpeed);
//                this.addMotorForce(3,-incrementSpeed);
//                this.addMotorForce(4,+incrementSpeed);
//            }else {
//                this.addMotorForce(1,+incrementSpeed);
//                this.addMotorForce(2,-incrementSpeed);
//                this.addMotorForce(3,+incrementSpeed);
//                this.addMotorForce(4,-incrementSpeed);
//            }
            //forward back right left movement
            this.addMotorForce(1,- playerRider.zza/20 + playerRider.xxa/20 ) ;
            this.addMotorForce(2,- playerRider.zza/20 - playerRider.xxa/20 );
            this.addMotorForce(3, + playerRider.zza/20 - playerRider.xxa/20 );
            this.addMotorForce(4, + playerRider.zza/20 + playerRider.xxa/20 );

            //actual structure
            //2----1
            //3----4

        }
    }

    private void setTargetYawAngle(float targetYaw) {
        this.targetYaw = targetYaw;
    }
    private float getTargetYawAngle() {
        return this.targetYaw;
    }


    @Override
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        if (player.getMainHandItem().isEmpty()){
            player.startRiding(this);
        }
    }


    public void elaborateInput(int key, int keyState) {
        //received input
        boolean pressedUpKey = key== GLFW.GLFW_KEY_SPACE && keyState > 0;
        boolean pressedDownKey = key == GLFW.GLFW_KEY_LEFT_CONTROL && keyState > 0;
        float force = 0.1F;
        if(pressedUpKey){
            addMotorForce(1,force);
            addMotorForce(2,force);
            addMotorForce(3,force);
            addMotorForce(4,force);
        }
        if(pressedDownKey){
            addMotorForce(1,-force);
            addMotorForce(2,-force);
            addMotorForce(3,-force);
            addMotorForce(4,-force);
        }
    }
}
