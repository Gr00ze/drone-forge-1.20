package com.Gr00ze.drones_mod.network;

import com.Gr00ze.drones_mod.entities.Drone;
import com.Gr00ze.drones_mod.entities.controllers.PIDController;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.PacketDistributor;

public record ConfigKParameterPacket(int entityId, int controllerId, PIDController.PIDParameter parameterType, double value) {
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeInt(this.controllerId);
        buf.writeEnum(this.parameterType);
        buf.writeDouble(this.value);
    }
    public static ConfigKParameterPacket decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int controllerId = buf.readInt();
        PIDController.PIDParameter parameter = buf.readEnum(PIDController.PIDParameter.class);
        double value = buf.readDouble();
        return new ConfigKParameterPacket(entityId, controllerId, parameter, value);
    }

    public static void handle(ConfigKParameterPacket msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            System.out.println("Handling message: " + msg.getClass());
            // Work that needs to be thread-safe (most work)
            ServerPlayer sender = ctx.getSender(); // the client that sent this packet
            System.out.printf("Sender: %s\n", sender);
            // Do stuff
            int entityId = msg.entityId();
            int controllerId = msg.controllerId();
            double value = msg.value();
            Level level = Minecraft.getInstance().level;
            if(level == null)
                throw new IllegalStateException(ConfigKParameterPacket.class +":Level is null");

            Entity entity = Minecraft.getInstance().level.getEntity(entityId);

            if (entity instanceof Drone drone) {
                drone.setKParameter(value, msg.parameterType, msg.controllerId);
                System.out.printf("Set value %.2e for %s in cId %d\n", value, msg.parameterType, msg.controllerId);
            }

            // Verifica se il codice è eseguito sul server prima di inviare il pacchetto
            if (ctx.getDirection().getReceptionSide().isServer()) {
                // Invia il pacchetto solo se il codice è eseguito sul server
                System.out.println("Invio i pacchetti ai client");
                PacketHandler.CHANNEL.send( new ConfigKParameterPacket(entityId, controllerId, msg.parameterType, value), PacketDistributor.ALL.noArg());
            }

        });
        ctx.setPacketHandled(true);
    }
}
