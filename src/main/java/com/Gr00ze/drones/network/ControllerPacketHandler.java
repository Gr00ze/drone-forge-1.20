package com.Gr00ze.drones.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

public class ControllerPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals

    );
    public static void init() {
        int id = 0; // Incrementa per ogni tipo di pacchetto

        // Registra il pacchetto personalizzato
        CHANNEL.registerMessage(
                id++,
                ControllerPacket.class,
                ControllerPacket::encode,
                ControllerPacket::decode,
                ControllerPacket::handle
        );
        CHANNEL.registerMessage(
                id++,
                PlayerControlsPacket.class,
                PlayerControlsPacket::encode,
                PlayerControlsPacket::decode,
                PlayerControlsPacket::handle
        );
    }

}
