package com.Gr00ze.drones.events;

import com.Gr00ze.drones.entities.GenericDrone;
import com.Gr00ze.drones.gui.Screens;
import com.Gr00ze.drones.items.DroneController;
import com.Gr00ze.drones.items.DroneFrame;
import com.Gr00ze.drones.network.ControllerPacketHandler;
import com.Gr00ze.drones.network.PlayerControlsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static com.Gr00ze.drones.DronesMod.MOD_ID;

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
        if (vehicle instanceof GenericDrone genericDrone){
            boolean pressedJump = event.getKey() == GLFW.GLFW_KEY_SPACE && event.getAction() > 0;
            boolean pressedDownKey = event.getKey() == GLFW.GLFW_KEY_LEFT_CONTROL && event.getAction() > 0;
            PlayerControlsPacket packet = new PlayerControlsPacket(genericDrone.getId(),pressedJump,pressedDownKey);
            ControllerPacketHandler.CHANNEL.sendToServer(packet);
        }


    }
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event ){
        ClientLevel level = Minecraft.getInstance().level;
        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();
        ///////////////////////////////////////////////
        if(itemStack.getItem() instanceof DroneController droneController && !player.isCrouching()){
            droneController.onRightClick(itemStack);
            int entity_id = itemStack.getOrCreateTag().getInt("DRONE_ID");
            System.out.println(("RE: Client: id set "+entity_id));
            if (level == null){
                System.out.println(("RE: Client: level nullo"));
                return;
            }
            Entity entity = level.getEntity(entity_id);
            if (entity instanceof GenericDrone genericDrone){
                //non fa partire il server
                Minecraft.getInstance().setScreen(Screens.getScreen(entity_id, genericDrone));

            }
        };
        ////////////////////////////////////////

    }


}
