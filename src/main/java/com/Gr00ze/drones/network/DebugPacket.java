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

public class  DebugPacket {
    private final int
            entityId,
            rotorId;
    private final float rotorSpeed;
    public DebugPacket(int entityId, int rotorId, float rotorSpeed) {
        this.entityId = entityId;
        this.rotorId = rotorId;
        this.rotorSpeed = rotorSpeed;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeInt(this.rotorId);
        buf.writeFloat(this.rotorSpeed);
        System.out.println("Mando: " + this.rotorSpeed);
        // Codifica altri campi del pacchetto se necessario
    }

    public static DebugPacket decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int rotorId = buf.readInt();
        float verticalSpeed = buf.readFloat();

        // Decodifica altri campi del pacchetto se necessario
        return new DebugPacket(entityId, rotorId, verticalSpeed);
    }

    public static void handle(DebugPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be thread-safe (most work)
            ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
            // Do stuff
            int entityId = msg.getEntityId();
            float verticalSpeed = msg.getRotorSpeed();

            if (sender != null) {
                ServerLevel world = sender.serverLevel(); // Ottieni il mondo in cui si trova il giocatore
                Entity entity = world.getEntity(entityId); // Ottieni l'entità dal suo ID

                if (entity instanceof GenericDrone genericDrone) {
                    switch (msg.getRotorId()){
                        case 1: genericDrone.setW1(verticalSpeed); break;
                        case 2: genericDrone.setW2(verticalSpeed); break;
                        case 3: genericDrone.setW3(verticalSpeed); break;
                        case 4: genericDrone.setW4(verticalSpeed); break;

                    }


                }
            }

        });
                ctx.get().setPacketHandled(true);
    }
    public float getRotorSpeed() {
        return rotorSpeed;
    }

    public int getEntityId() {return entityId;}
    public int getRotorId() {return rotorId;}
}
