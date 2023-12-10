package com.Gr00ze.drones.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.Gr00ze.drones.DronesMod.MOD_ID;
public class Init {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS , MOD_ID);

    public static final RegistryObject<Item> DEBUG_STUFF = ITEMS.register(
            "debug_stuff", DebugStuff::new
    );


    public static void register(IEventBus iEventBus){
        ITEMS.register(iEventBus);
    }
}
