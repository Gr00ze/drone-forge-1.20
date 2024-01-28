package com.Gr00ze.drones.blocks;

import com.Gr00ze.drones.DronesMod;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Blocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DronesMod.MOD_ID);

    public static final RegistryObject<Block> TABLE_BLOCK = BLOCKS.register(
            "assembly_table", AssemblyTable::new);


    public static void register(IEventBus iEventBus){
        BLOCKS.register(iEventBus);
    }
}
