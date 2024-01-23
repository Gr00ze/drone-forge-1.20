package com.Gr00ze.drones.events;

import com.Gr00ze.drones.entities.GenericDrone;
import com.Gr00ze.drones.network.ControllerPacketHandler;
import com.Gr00ze.drones.network.PlayerControlsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event ){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        ItemStack itemStack = player.getMainHandItem();
        System.out.println("Tentativo cavalcaggio");
        boolean isDrone = entity instanceof Entity;
        if (isDrone && itemStack.isEmpty()){
            player.startRiding(entity);
        }
    }
    @SubscribeEvent
    public static void onJumpPlayer(LivingEvent.LivingJumpEvent event){
        System.out.println(event.getEntity().getName()+" sta saltando;");
    }
    @SubscribeEvent
    public static void playerControls(InputEvent.Key event){

//        System.out.print("event.getAction() ");
//        System.out.println(event.getAction());
//        System.out.print("event.getKey() ");
//        System.out.println(event.getKey());
//        System.out.print("event.getModifiers() ");
//        System.out.println(event.getModifiers());
//        System.out.print("event.getScanCode() ");
//        System.out.println(event.getScanCode());

        Player localPlayer = Minecraft.getInstance().player;
        if(localPlayer == null || !localPlayer.isPassenger())return;
        Entity vehicle = localPlayer.getVehicle();
        if (vehicle instanceof GenericDrone genericDrone){
            boolean pressedJump = event.getKey() == GLFW.GLFW_KEY_SPACE && event.getAction() > 0;
            PlayerControlsPacket packet = new PlayerControlsPacket(genericDrone.getId(),pressedJump);
            ControllerPacketHandler.CHANNEL.sendToServer(packet);
        }


    }
}
