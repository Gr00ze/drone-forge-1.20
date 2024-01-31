package com.Gr00ze.drones_mod;

import net.minecraftforge.network.*;

public class PacketExample {
    private static final int PROTOCOL_VERSION = 1;
    public static final SimpleChannel INSTANCE = ChannelBuilder
            .named("example_channel_name")
            .networkProtocolVersion(PROTOCOL_VERSION)
            .simpleChannel();

    public static void init(){
        //INSTANCE.messageBuilder();

    }
}
