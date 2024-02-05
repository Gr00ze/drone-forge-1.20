package com.Gr00ze.drones_mod.network;

import com.Gr00ze.drones_mod.entities.Drone;
import com.Gr00ze.drones_mod.entities.GenericDrone;
import com.Gr00ze.drones_mod.entities.controllers.PIDController;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record ControllerPacket2(int entityId,int controllerId, PIDController.PIDParameter parameterType, float value) {
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeInt(this.controllerId);
        buf.writeEnum(this.parameterType);
        buf.writeFloat(this.value);
    }
    public static ControllerPacket2 decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int controllerId = buf.readInt();
        PIDController.PIDParameter parameter = buf.readEnum(PIDController.PIDParameter.class);
        float value = buf.readFloat();
        return new ControllerPacket2(entityId, controllerId, parameter, value);
    }

    public static void handle(ControllerPacket2 msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            System.out.println("Handling message: " + msg.getClass());
            // Work that needs to be thread-safe (most work)
            ServerPlayer sender = ctx.getSender(); // the client that sent this packet
            // Do stuff
            int entityId = msg.entityId();
            int controllerId = msg.controllerId();
            float value = msg.value();

            if (sender != null) {
                ServerLevel world = sender.serverLevel(); // Ottieni il mondo in cui si trova il giocatore
                Entity entity = world.getEntity(entityId);
                // Ottieni l'entità dal suo ID

                if (entity instanceof Drone drone) {
                    PIDController controller = drone.getAllPIDControllers()[controllerId];
                    switch (msg.parameterType) {
                        case KP:
                            controller.Kp = value;
                            break;
                        case KI:
                            controller.Ki = value;
                            break;
                        case KD:
                            controller.Kd = value;
                            break;

                    }


                }
            }

        });
        ctx.setPacketHandled(true);
    }
}
