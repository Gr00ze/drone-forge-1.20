package com.Gr00ze.drones;

import com.Gr00ze.drones.blocks.Blocks;
import com.Gr00ze.drones.entities.Init;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DronesMod.MOD_ID)
public class DronesMod {
    public static final String MOD_ID = "DroneMod";


    public DronesMod (){
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus modEventbus = FMLJavaModLoadingContext.get().getModEventBus();
        Blocks.register(modEventbus);
        Init.register(modEventbus);
    }
}
