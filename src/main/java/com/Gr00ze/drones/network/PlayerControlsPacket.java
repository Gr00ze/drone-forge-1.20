package com.Gr00ze.drones.network;

import com.Gr00ze.drones.entities.GenericDrone;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerControlsPacket {
    private int entityId;
    private boolean isAcceleratingY;

    public PlayerControlsPacket(int entityId, boolean isAcceleratingY) {
        this.entityId = entityId;
        this.isAcceleratingY = isAcceleratingY;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeBoolean(this.isAcceleratingY);
        // Codifica altri campi del pacchetto se necessario
    }

    public static PlayerControlsPacket decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        boolean isAcceleratingY = buf.readBoolean();

        // Decodifica altri campi del pacchetto se necessario
        return new PlayerControlsPacket(entityId, isAcceleratingY);
    }

    public static void handle(PlayerControlsPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be thread-safe (most work)
            ServerPlayer sender = ctx.get().getSender(); // the client that sent this packet
            // Do stuff
            int entityId = msg.getEntityId();
            boolean isAcceleratingY = msg.isAcceleratingY();

            if (sender != null) {
                ServerLevel world = sender.serverLevel(); // Ottieni il mondo in cui si trova il giocatore
                Entity entity = world.getEntity(entityId); // Ottieni l'entità dal suo ID

                if (entity != null) {
                    GenericDrone genericDrone = (GenericDrone)entity;
                    genericDrone.isAcceleratingY(msg.isAcceleratingY);
                }
            }

        });
        ctx.get().setPacketHandled(true);
    }
    public boolean isAcceleratingY() {
        return isAcceleratingY;
    }

    public int getEntityId() {
        return entityId;
    }
}
