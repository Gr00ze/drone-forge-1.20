package com.Gr00ze.drones.network;

import com.Gr00ze.drones.entities.GenericDrone;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketDecoder;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class DebugPacket {
    private int entityId;
    private int verticalSpeed;

    public DebugPacket(int entityId, int verticalSpeed) {
        this.entityId = entityId;
        this.verticalSpeed = verticalSpeed;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeInt(this.verticalSpeed);
        // Codifica altri campi del pacchetto se necessario
    }

    public static DebugPacket decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int verticalSpeed = buf.readInt();

        // Decodifica altri campi del pacchetto se necessario
        return new DebugPacket(entityId, verticalSpeed);
    }

    public static void handle(DebugPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be thread-safe (most work)
            ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
            // Do stuff
            int entityId = msg.getEntityId();
            int verticalSpeed = msg.getVerticalSpeed();

            if (sender != null) {
                ServerLevel world = sender.serverLevel(); // Ottieni il mondo in cui si trova il giocatore
                Entity entity = world.getEntity(msg.getEntityId()); // Ottieni l'entità dal suo ID

                if (entity != null) {
                    GenericDrone genericDrone = (GenericDrone)entity;
                    genericDrone.setW1(msg.getVerticalSpeed());
                }
            }

        });
                ctx.get().setPacketHandled(true);
    }
    public int getVerticalSpeed() {
        return verticalSpeed;
    }

    public int getEntityId() {
        return entityId;
    }
}
