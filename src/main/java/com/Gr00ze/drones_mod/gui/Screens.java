package com.Gr00ze.drones_mod.gui;

import com.Gr00ze.drones_mod.entities.GenericDrone;

public class Screens {
    public static ControllerScreen getScreen(int entity_id, GenericDrone genericDrone){
        return new ControllerScreen(entity_id, genericDrone);
    }
}
