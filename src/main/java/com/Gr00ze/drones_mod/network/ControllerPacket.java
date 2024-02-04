package com.Gr00ze.drones_mod.network;

import com.Gr00ze.drones_mod.entities.Drone;
import com.Gr00ze.drones_mod.entities.GenericDrone;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record ControllerPacket(int entityId, int rotorId, float rotorSpeed) {

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeInt(this.rotorId);
        buf.writeFloat(this.rotorSpeed);
        System.out.println("Encode: " + this.rotorSpeed);
        // Codifica altri campi del pacchetto se necessario
    }

    public static ControllerPacket decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int rotorId = buf.readInt();
        float rotorSpeed = buf.readFloat();
        System.out.println("Decode: " + rotorSpeed);

        // Decodifica altri campi del pacchetto se necessario
        return new ControllerPacket(entityId, rotorId, rotorSpeed);
    }

    public static void handle(ControllerPacket msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            System.out.println("Handling message: " + msg.getClass());
            // Work that needs to be thread-safe (most work)
            ServerPlayer sender = ctx.getSender(); // the client that sent this packet
            // Do stuff
            int entityId = msg.entityId();
            float verticalSpeed = msg.rotorSpeed();

            if (sender != null) {
                ServerLevel world = sender.serverLevel(); // Ottieni il mondo in cui si trova il giocatore
                Entity entity = world.getEntity(entityId); // Ottieni l'entità dal suo ID

                if (entity instanceof GenericDrone genericDrone) {
                    switch (msg.rotorId()) {
                        case 1:
                            genericDrone.setW1(verticalSpeed);
                            break;
                        case 2:
                            genericDrone.setW2(verticalSpeed);
                            break;
                        case 3:
                            genericDrone.setW3(verticalSpeed);
                            break;
                        case 4:
                            genericDrone.setW4(verticalSpeed);
                            break;
                    }
                }
                if (entity instanceof Drone drone) {
                    drone.setMotorForce(msg.rotorId, verticalSpeed);
                }
            }

        });
        ctx.setPacketHandled(true);
    }
}
