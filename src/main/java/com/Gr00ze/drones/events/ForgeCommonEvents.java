package com.Gr00ze.drones.events;

import com.Gr00ze.drones.blocks.AssemblyTable;
import com.Gr00ze.drones.entities.GenericDrone;
import com.Gr00ze.drones.items.DroneController;
import com.Gr00ze.drones.items.DroneFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Gr00ze.drones.DronesMod.MOD_ID;
import static com.Gr00ze.drones.blocks.Blocks.TABLE_BLOCK;
import static com.Gr00ze.drones.entities.EntityInit.GENERIC_DRONE;
import static net.minecraft.world.InteractionHand.MAIN_HAND;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEvents {
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event ){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem() instanceof DroneController controller){
            controller.onEntityInteract(event);
        }
    }


    @SubscribeEvent
    public static void onJumpPlayer(LivingEvent.LivingJumpEvent event){
        //System.out.println(event.getEntity().getName()+" sta saltando;");
    }


    @SubscribeEvent
    public static void onBlockInteraction(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();

        BlockPos blockPos = event.getPos();
        Level level = event.getLevel();
        MinecraftServer server = level.getServer();
        if ( server == null ) return;
        ServerLevel serverLevel = server.overworld();

        if( heldItem.getItem() instanceof DroneFrame droneFrame && event.getLevel().getBlockState(blockPos).is(TABLE_BLOCK.get()) && event.getHand() == MAIN_HAND){
            droneFrame.useAction();
            if (!player.isCreative())
                heldItem.shrink(1);
            GENERIC_DRONE.get().spawn(serverLevel,blockPos.above(1), MobSpawnType.EVENT);

        }
    }
}
