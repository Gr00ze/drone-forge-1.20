package com.Gr00ze.drones.items;


import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import static com.Gr00ze.drones.DronesMod.MOD_ID;
import static com.Gr00ze.drones.items.Init.*;

public class DroneCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,MOD_ID);
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = TABS.register("example", () -> CreativeModeTab.builder()
            // Set name of tab to display
            .title(Component.translatable("item_group." + MOD_ID + ".example"))
            // Set icon of creative tab
            .icon(() -> new ItemStack(DRONE_FRAME.get()))
            // Add default items to tab
            .displayItems((params, output) -> {
                output.accept(DRONE_FRAME.get());
                output.accept(DRONE_CONTROLLER.get());
                output.accept(DRONE_MOTOR.get());
                output.accept(TABLE_BLOCK_ITEM.get());

            })
            .build()
    );

    public static void register(IEventBus eventBus){
        TABS.register(eventBus);
    }
}
