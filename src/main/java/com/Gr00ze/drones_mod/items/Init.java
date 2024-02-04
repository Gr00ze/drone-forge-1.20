package com.Gr00ze.drones_mod.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.Gr00ze.drones_mod.DronesMod.MOD_ID;
import static com.Gr00ze.drones_mod.blocks.Blocks.TABLE_BLOCK;

public class Init {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS , MOD_ID);

    public static final RegistryObject<Item> GDRONE_CONTROLLER = ITEMS.register(
            "generic_controller", GenericDroneController::new
    );
    public static final RegistryObject<Item> DRONE_CONTROLLER = ITEMS.register(
            "controller", DroneController::new
    );

    public static final RegistryObject<Item> DRONE_FRAME = ITEMS.register(
            "frame", DroneFrame::new
    );

    public static final RegistryObject<Item> DRONE_MOTOR = ITEMS.register(
            "motor", Motor::new
    );
    public static final RegistryObject<BlockItem> TABLE_BLOCK_ITEM = ITEMS.register(
            "table_block", () -> new BlockItem(TABLE_BLOCK.get(),new Item.Properties())
    );




    public static void register(IEventBus iEventBus){
        ITEMS.register(iEventBus);
    }
}
