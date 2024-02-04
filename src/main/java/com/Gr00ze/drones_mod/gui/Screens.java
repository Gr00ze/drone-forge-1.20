package com.Gr00ze.drones_mod.gui;

import com.Gr00ze.drones_mod.entities.Drone;
import com.Gr00ze.drones_mod.entities.GenericDrone;

public class Screens {
    public static GenericControllerScreen getScreen(int entity_id, GenericDrone genericDrone){
        return new GenericControllerScreen(entity_id, genericDrone);
    }
    public static ControllerScreen getScreen(int entity_id, Drone drone){
        return new ControllerScreen(entity_id, drone);
    }
}
