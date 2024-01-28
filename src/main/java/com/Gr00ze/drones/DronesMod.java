package com.Gr00ze.drones;

import com.Gr00ze.drones.blocks.Blocks;
import com.Gr00ze.drones.entities.EntityInit;
import com.Gr00ze.drones.gui.MyMenu;
import com.Gr00ze.drones.items.DroneCreativeTab;
import com.Gr00ze.drones.network.ControllerPacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DronesMod.MOD_ID)
public class DronesMod {
    public static final String MOD_ID = "drones_mod";


    public DronesMod (){
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus modEventbus = FMLJavaModLoadingContext.get().getModEventBus();
        Blocks.register(modEventbus);
        EntityInit.register(modEventbus);
        com.Gr00ze.drones.items.Init.register(modEventbus);
        MyMenu.register(modEventbus);
        DroneCreativeTab.register(modEventbus);
        ControllerPacketHandler.init();

    }
}
