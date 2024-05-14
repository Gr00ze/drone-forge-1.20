package com.Gr00ze.drones_mod.events;

import com.Gr00ze.drones_mod.entities.AbstractDrone;
import com.Gr00ze.drones_mod.entities.Drone;
import com.Gr00ze.drones_mod.entities.GenericDrone;
import com.Gr00ze.drones_mod.gui.Screens;
import com.Gr00ze.drones_mod.items.DroneController;
import com.Gr00ze.drones_mod.items.GenericDroneController;
import com.Gr00ze.drones_mod.network.KeyInputPacket;
import com.Gr00ze.drones_mod.network.PlayerControlsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.Event;
import org.lwjgl.glfw.GLFW;

import static com.Gr00ze.drones_mod.DronesMod.MOD_ID;
import static com.Gr00ze.drones_mod.DronesMod.printDebug;
import static com.Gr00ze.drones_mod.network.PacketHandler.sendToServer;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void playerControls(InputEvent.Key event){

        //System.out.print("event.getAction() ");
        //System.out.println(event.getAction());
        //System.out.print("event.getKey() ");
        //System.out.println(event.getKey());
        //System.out.print("event.getModifiers() ");
        //System.out.println(event.getModifiers());
        //System.out.print("event.getScanCode() ");
        //System.out.println(event.getScanCode());

        Player localPlayer = Minecraft.getInstance().player;
        if(localPlayer == null || !localPlayer.isPassenger())return;
        Entity vehicle = localPlayer.getVehicle();
        ///////////////////////////////////////////////////////////////////
        if (vehicle instanceof GenericDrone genericDrone){
            boolean pressedJump = event.getKey() == GLFW.GLFW_KEY_SPACE && event.getAction() > 0;
            boolean pressedDownKey = event.getKey() == GLFW.GLFW_KEY_LEFT_CONTROL && event.getAction() > 0;
            PlayerControlsPacket packet = new PlayerControlsPacket(genericDrone.getId(),pressedJump,pressedDownKey);
            sendToServer(packet);
        }
        //////////////////////////////////////////////////////////////////
        if (vehicle instanceof Drone drone){
            KeyInputPacket packet = new KeyInputPacket(drone.getId(), event.getKey(), event.getAction());
            sendToServer(packet);
        }

    }
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event ){
        ClientLevel level = Minecraft.getInstance().level;
        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();
        boolean isClient = true;
        if(level == null){
            printDebug("CLIENT","Found Level Null");
            return;
        }

        ///////////////////////////////////////////////
        if(itemStack.getItem() instanceof DroneController droneController && !player.isCrouching()){
            droneController.onRightClick(itemStack);
            int entity_id = itemStack.getOrCreateTag().getInt("DRONE_ID");
            printDebug(isClient,"RightClick: id set %d",entity_id);
            Entity entity = level.getEntity(entity_id);
            if (entity instanceof Drone drone){
                Minecraft.getInstance().setScreen(Screens.getScreen(entity_id, drone));

            }
        }
        ////////////////////////////////////////
        if(itemStack.getItem() instanceof GenericDroneController droneController && !player.isCrouching()){
            droneController.onRightClick(itemStack);
            int entity_id = itemStack.getOrCreateTag().getInt("DRONE_ID");
            printDebug(isClient,"RightClick: id set %d",entity_id);
            Entity entity = level.getEntity(entity_id);
            if (entity instanceof GenericDrone drone){
                Minecraft.getInstance().setScreen(Screens.getScreen(entity_id, drone));

            }
        }
        ////////////////////////////////////////

    }

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGuiEvent event) {
        //sus
        //event.getGuiGraphics().drawString(Minecraft.getInstance().font,"SUS",0,0,0xFFFFFF);
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null)return;
        Entity entity = player.getVehicle();
        float playerYR = player.getYRot();
        event.getGuiGraphics().drawString(Minecraft.getInstance().font,"" + playerYR, 0,0,0xFFFFFF);
        if (entity instanceof Drone drone){
            float droneYaw = drone.getAngle(AbstractDrone.DroneAngle.YAW) * (float)(180 / Math.PI);
            event.getGuiGraphics().drawString(Minecraft.getInstance().font,"" + droneYaw, 0,20,0xFFFFFF);

        }
    }

    @SubscribeEvent
    public static void onClientSetup(Event event){
        //All events
    }




}
