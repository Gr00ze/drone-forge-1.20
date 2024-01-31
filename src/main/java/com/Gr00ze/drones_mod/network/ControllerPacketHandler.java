package com.Gr00ze.drones_mod.network;

import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

import static com.Gr00ze.drones_mod.DronesMod.MOD_ID;

public class ControllerPacketHandler {
    private static final int PROTOCOL_VERSION = 1;
    public static final SimpleChannel CHANNEL = ChannelBuilder.named(
            new ResourceLocation(MOD_ID, "main"))
            .networkProtocolVersion(PROTOCOL_VERSION)
            .clientAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .serverAcceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
            .optional()
            .connectionHandler(ControllerPacketHandler::connectionHandler)
            .simpleChannel();

    public static void init() {
        int id = 0; // Incrementa per ogni tipo di pacchetto

        // Registra il pacchetto personalizzato
        CHANNEL.messageBuilder(ControllerPacket.class, id++)
                .decoder(ControllerPacket::decode)
                .encoder(ControllerPacket::encode)
                .consumerNetworkThread(ControllerPacket::handle)
                .consumerMainThread(ControllerPacket::handle)
                .add();

        CHANNEL.messageBuilder(PlayerControlsPacket.class, id++)
                .decoder(PlayerControlsPacket::decode)
                .encoder(PlayerControlsPacket::encode)
                .consumerNetworkThread(PlayerControlsPacket::handle)
                .consumerMainThread(PlayerControlsPacket::handle)
                .add();

    }
    public static void connectionHandler(Connection connection){
        System.out.println("Ero nascosto");

    }

    public static void sendToServer(Object packet){
        CHANNEL.send(packet, PacketDistributor.SERVER.noArg());
    }
}




