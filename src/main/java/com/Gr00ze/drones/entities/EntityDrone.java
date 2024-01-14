package com.Gr00ze.drones.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EntityDrone extends Entity {
    public EntityDrone(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }
    @Override
    public void tick() {
        super.tick();
        solidCollision();
    }

    private void solidCollision() {
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox());
        if (list.isEmpty()) return;

        for (Entity entity : list) {
            //System.out.println(entity);
            Vec3 vec = entity.getDeltaMovement();
            System.out.println(vec);
            if (vec.y() < 0) {
                vec.add(0, -vec.y(), 0);
                entity.setDeltaMovement(new Vec3(vec.x,0,vec.z));
                entity.setOnGround(true);
            }
            //entity.addDeltaMovement(new Vec3(0,0.08,0));
        }

    }
    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

    }
}
