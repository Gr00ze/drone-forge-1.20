package com.Gr00ze.drones_mod.network;

import com.Gr00ze.drones_mod.entities.Drone;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record KeyInputPacket(int entityID, int key, int state) {
    public void encode(FriendlyByteBuf buffer){
        buffer.writeInt(entityID);
        buffer.writeInt(key);
        buffer.writeInt(state);
    }

    public static KeyInputPacket decode(FriendlyByteBuf buffer){
        return new KeyInputPacket(buffer.readInt(),buffer.readInt(),buffer.readInt());
    }

    public static void handle(KeyInputPacket msg, CustomPayloadEvent.Context ctx){
        ctx.enqueueWork(()->{
            ServerPlayer sender = ctx.getSender();
            if (sender == null) return;
            ServerLevel level = sender.serverLevel();
            Entity entity = level.getEntity(msg.entityID());
            if (entity instanceof Drone drone){
                drone.elaborateInput(msg.key(),msg.state());
            }
        });
        ctx.setPacketHandled(true);
    }
}
