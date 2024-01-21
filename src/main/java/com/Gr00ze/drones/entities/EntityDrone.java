package com.Gr00ze.drones.entities;


import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.extensions.IForgeEntity;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EntityDrone extends Entity {

    public EntityDrone(EntityType<?> entityType, Level level) {

        super(entityType, level);

    }
    @Override
    public void tick() {
        super.tick();
        solidCollision();


    }


//    public void travel(Vec3 p_21280_) {
//        if (this.isControlledByLocalInstance()) {
//            double d0 = 0.08D;
//            boolean flag = this.getDeltaMovement().y <= 0.0D;
//
//
//            FluidState fluidstate = this.level().getFluidState(this.blockPosition());
//            if ((this.isInWater() || (this.isInFluidType(fluidstate) && fluidstate.getFluidType() != net.minecraftforge.common.ForgeMod.LAVA_TYPE.get())) ) {
//                if (this.isInWater() || (this.isInFluidType(fluidstate))) {
//                    double d9 = this.getY();
//                    float f4 = this.isSprinting() ? 0.9F : 0.1F;
//                    float f5 = 0.02F;
//                    float f6 = (float) 0.1F;
//                    if (!this.onGround()) {
//                        f6 *= 0.5F;
//                    }
//
//                        f4 += (0.54600006F - f4) * f6 / 3.0F;
//                        f5 += (3 - f5) * f6 / 3.0F;
//
//
//                    this.moveRelative(f5, p_21280_);
//                    this.move(MoverType.SELF, this.getDeltaMovement());
//                    Vec3 vec36 = this.getDeltaMovement();
//                    if (this.horizontalCollision) {
//                        vec36 = new Vec3(vec36.x, 0.2D, vec36.z);
//                    }
//
//                    this.setDeltaMovement(vec36.multiply((double)f4, (double)0.8F, (double)f4));
//                    Vec3 vec32 = this.getFluidFallingAdjustedMovement(d0, flag, this.getDeltaMovement());
//                    this.setDeltaMovement(vec32);
//                    if (this.horizontalCollision && this.isFree(vec32.x, vec32.y + (double)0.6F - this.getY() + d9, vec32.z)) {
//                        this.setDeltaMovement(vec32.x, (double)0.3F, vec32.z);
//                    }
//                }
//            }  else if (this.isFallFlying()) {
//                this.checkSlowFallDistance();
//                Vec3 vec3 = this.getDeltaMovement();
//                Vec3 vec31 = this.getLookAngle();
//                float f = this.getXRot() * ((float)Math.PI / 180F);
//                double d1 = Math.sqrt(vec31.x * vec31.x + vec31.z * vec31.z);
//                double d3 = vec3.horizontalDistance();
//                double d4 = vec31.length();
//                double d5 = Math.cos((double)f);
//                d5 = d5 * d5 * Math.min(1.0D, d4 / 0.4D);
//                vec3 = this.getDeltaMovement().add(0.0D, d0 * (-1.0D + d5 * 0.75D), 0.0D);
//                if (vec3.y < 0.0D && d1 > 0.0D) {
//                    double d6 = vec3.y * -0.1D * d5;
//                    vec3 = vec3.add(vec31.x * d6 / d1, d6, vec31.z * d6 / d1);
//                }
//
//                if (f < 0.0F && d1 > 0.0D) {
//                    double d10 = d3 * (double)(-Mth.sin(f)) * 0.04D;
//                    vec3 = vec3.add(-vec31.x * d10 / d1, d10 * 3.2D, -vec31.z * d10 / d1);
//                }
//
//                if (d1 > 0.0D) {
//                    vec3 = vec3.add((vec31.x / d1 * d3 - vec3.x) * 0.1D, 0.0D, (vec31.z / d1 * d3 - vec3.z) * 0.1D);
//                }
//
//                this.setDeltaMovement(vec3.multiply((double)0.99F, (double)0.98F, (double)0.99F));
//                this.move(MoverType.SELF, this.getDeltaMovement());
//                if (this.horizontalCollision && !this.level().isClientSide) {
//                    double d11 = this.getDeltaMovement().horizontalDistance();
//                    double d7 = d3 - d11;
//                    float f1 = (float)(d7 * 10.0D - 3.0D);
//                    if (f1 > 0.0F) {
//
//                        this.hurt(this.damageSources().flyIntoWall(), f1);
//                    }
//                }
//
//                if (this.onGround() && !this.level().isClientSide) {
//                    this.setSharedFlag(7, false);
//                }
//            } else {
//                BlockPos blockpos = this.getBlockPosBelowThatAffectsMyMovement();
//                float f2 = this.level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getFriction(level(), this.getBlockPosBelowThatAffectsMyMovement(), this);
//                float f3 = this.onGround() ? f2 * 0.91F : 0.91F;
//                Vec3 vec35 = this.handleRelativeFrictionAndCalculateMovement(p_21280_, f2);
//                double d2 = vec35.y;
//                if (this.hasEffect(MobEffects.LEVITATION)) {
//                    d2 += (0.05D * (double)(this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - vec35.y) * 0.2D;
//                } else if (this.level().isClientSide && !this.level().hasChunkAt(blockpos)) {
//                    if (this.getY() > (double)this.level().getMinBuildHeight()) {
//                        d2 = -0.1D;
//                    } else {
//                        d2 = 0.0D;
//                    }
//                } else if (!this.isNoGravity()) {
//                    d2 -= d0;
//                }
//
//                if (this.shouldDiscardFriction()) {
//                    this.setDeltaMovement(vec35.x, d2, vec35.z);
//                } else {
//                    this.setDeltaMovement(vec35.x * (double)f3, d2 * (double)0.98F, vec35.z * (double)f3);
//                }
//            }
//        }
//
//        this.calculateEntityAnimation(this instanceof FlyingAnimal);
//    }
//    public Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 p_21075_, float p_21076_) {
//        this.moveRelative(this.getFrictionInfluencedSpeed(p_21076_), p_21075_);
//        this.setDeltaMovement(this.handleOnClimbable(this.getDeltaMovement()));
//        this.move(MoverType.SELF, this.getDeltaMovement());
//        Vec3 vec3 = this.getDeltaMovement();
//        if ((this.horizontalCollision || this.jumping) && (this.onClimbable() || this.getFeetBlockState().is(Blocks.POWDER_SNOW) && PowderSnowBlock.canEntityWalkOnPowderSnow(this))) {
//            vec3 = new Vec3(vec3.x, 0.2D, vec3.z);
//        }
//
//        return vec3;
//    }

    private void solidCollision() {
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox());
        if (list.isEmpty()) return;

        for (Entity entity : list) {
            Vec3 vec = entity.getDeltaMovement();
            entity.setPos(entity.getX(),this.getY() + this.getBoundingBox().getYsize(),entity.getZ());
            entity.setDeltaMovement(vec.x,vec.y < 0 ? 0 : vec.y,vec.z);
            entity.resetFallDistance();
            entity.setOnGround(true);
        }

    }
    public boolean isFallFlying() {
        return this.getSharedFlag(7);
    }
    public Vec3 getFluidFallingAdjustedMovement(double p_20995_, boolean p_20996_, Vec3 p_20997_) {
        if (!this.isNoGravity() && !this.isSprinting()) {
            double d0;
            if (p_20996_ && Math.abs(p_20997_.y - 0.005D) >= 0.003D && Math.abs(p_20997_.y - p_20995_ / 16.0D) < 0.003D) {
                d0 = -0.003D;
            } else {
                d0 = p_20997_.y - p_20995_ / 16.0D;
            }

            return new Vec3(p_20997_.x, d0, p_20997_.z);
        } else {
            return p_20997_;
        }
    }
    @Override
    public @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
        System.out.println("Interazione");
        return InteractionResult.SUCCESS;
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
