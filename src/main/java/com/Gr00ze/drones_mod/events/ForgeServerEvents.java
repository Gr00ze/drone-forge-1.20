package com.Gr00ze.drones_mod.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

import static com.Gr00ze.drones_mod.DronesMod.MOD_ID;

@Mod.EventBusSubscriber(value = Dist.DEDICATED_SERVER, modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeServerEvents {


}
