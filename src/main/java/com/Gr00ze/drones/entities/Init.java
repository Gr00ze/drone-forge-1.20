package com.Gr00ze.drones.entities;

import com.Gr00ze.drones.DronesMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

public class Init {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES , MOD_ID);

    public static final ResourceLocation GENERIC_DRONE_RL = new ResourceLocation(MOD_ID,"generic_drone");
    public static final RegistryObject<EntityType<GenericDrone>> GENERIC_DRONE = ENTITIES.register("generic_drone",
            () -> EntityType.Builder.of(GenericDrone::new, MobCategory.CREATURE)
                    .build(GENERIC_DRONE_RL.toString()));

    public static void register(IEventBus iEventBus){
        ENTITIES.register(iEventBus);

    }
}
