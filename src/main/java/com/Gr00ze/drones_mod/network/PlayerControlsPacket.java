package com.Gr00ze.drones_mod.network;

import com.Gr00ze.drones_mod.entities.Drone;
import com.Gr00ze.drones_mod.entities.GenericDrone;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.network.CustomPayloadEvent;


import java.util.function.Supplier;

public class PlayerControlsPacket {
    private int entityId;
    private final boolean isJumpKeyPressed,isDownKeyPressed;

    public PlayerControlsPacket(int entityId, boolean isJumpKeyPressed, boolean isDownKeyPressed) {
        this.entityId = entityId;
        this.isJumpKeyPressed = isJumpKeyPressed;
        this.isDownKeyPressed = isDownKeyPressed;
    }

    public void encode(FriendlyByteBuf buf) {
        System.out.println("Encode: " + this.getClass());
        buf.writeInt(this.entityId);
        buf.writeBoolean(this.isJumpKeyPressed);
        buf.writeBoolean(this.isDownKeyPressed);
        // Codifica altri campi del pacchetto se necessario
    }

    public static PlayerControlsPacket decode(FriendlyByteBuf buf) {
        System.out.println("Decode: " + PlayerControlsPacket.class);
        int entityId = buf.readInt();
        boolean isJumpKeyPressed = buf.readBoolean();
        boolean isDownKeyPressed = buf.readBoolean();

        // Decodifica altri campi del pacchetto se necessario
        return new PlayerControlsPacket(entityId, isJumpKeyPressed, isDownKeyPressed);
    }

    public static void handle(PlayerControlsPacket msg, CustomPayloadEvent.Context ctx) {

        ctx.enqueueWork(() -> {
            System.out.println("worka");
            // Work that needs to be thread-safe (most work)
            ServerPlayer sender = ctx.getSender(); // the client that sent this packet
            // Do stuff
            int entityId = msg.getEntityId();
            boolean isJumpKeyPressed = msg.isJumpKeyPressed();
            boolean isDownKeyPressed = msg.isDownKeyPressed();

            if (sender != null) {
                ServerLevel world = sender.serverLevel(); // Ottieni il mondo in cui si trova il giocatore
                Entity entity = world.getEntity(entityId); // Ottieni l'entità dal suo ID

                if (entity != null) {
                    if (entity instanceof GenericDrone genericDrone) {
                        genericDrone.driverWantGoUp(isJumpKeyPressed);
                        genericDrone.driverWantGoDown(isDownKeyPressed);
                    }
                    if (entity instanceof Drone drone) {

                    }
                }
            }

        });
        ctx.setPacketHandled(true);
    }
    public boolean isJumpKeyPressed() {
        return isJumpKeyPressed;
    }
    public boolean isDownKeyPressed() {
        return isDownKeyPressed;
    }

    public int getEntityId() {
        return entityId;
    }
}
