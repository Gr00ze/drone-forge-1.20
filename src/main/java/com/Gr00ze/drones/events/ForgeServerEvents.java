package com.Gr00ze.drones.events;

import com.Gr00ze.drones.items.DroneFrame;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.Gr00ze.drones.DronesMod.MOD_ID;
import static com.Gr00ze.drones.blocks.Blocks.TABLE_BLOCK;
import static com.Gr00ze.drones.entities.EntityInit.GENERIC_DRONE;
@Mod.EventBusSubscriber(value = Dist.DEDICATED_SERVER, modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeServerEvents {


}
