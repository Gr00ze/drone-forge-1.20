package com.Gr00ze.drones_mod;

import com.Gr00ze.drones_mod.blocks.Blocks;
import com.Gr00ze.drones_mod.entities.EntityInit;

import com.Gr00ze.drones_mod.gui.MyMenu;
import com.Gr00ze.drones_mod.items.DroneCreativeTab;
import com.Gr00ze.drones_mod.network.PacketHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import static com.Gr00ze.drones_mod.items.Init.DRONE_CONTROLLER;

@Mod(DronesMod.MOD_ID)
public class DronesMod {
    public static final String MOD_ID = "drones_mod";
    private static final Logger LOGGER = LogUtils.getLogger();


    public DronesMod (){
        LOGGER.info("Loading drones mod");
        MinecraftForge.EVENT_BUS.register(this);


        IEventBus modEventbus = FMLJavaModLoadingContext.get().getModEventBus();
        LOGGER.info("Loading drones block");
        Blocks.register(modEventbus);
        LOGGER.info("Loading drones entity");
        EntityInit.register(modEventbus);
        LOGGER.info("Loading drones item");
        com.Gr00ze.drones_mod.items.Init.register(modEventbus);
        LOGGER.info("Loading drones menu");
        MyMenu.register(modEventbus);
        LOGGER.info("Loading drones tab");
        DroneCreativeTab.register(modEventbus);
        LOGGER.info("Loading drones network");
        PacketHandler.init();
    }



    public static void printDebug(String side, @NotNull String value, Object ...object){

        StringBuilder finalValue = new StringBuilder(value + "\n");
        finalValue.insert(0,"["+side+"]:");
        System.out.printf(finalValue.toString(),object);
    }
    public static void printDebug(boolean isClient, @NotNull String value, Object ...object){

        StringBuilder finalValue = new StringBuilder(value + "\n");

        if(isClient){
            finalValue.insert(0,"[CLIENT]:");
        }else{
            finalValue.insert(0,"[SERVER]:");
        }
        System.out.printf(finalValue.toString(),object);
    }


}
